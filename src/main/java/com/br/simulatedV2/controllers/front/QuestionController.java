package com.br.simulatedV2.controllers.front;

import com.br.simulatedV2.Enum.Type;
import com.br.simulatedV2.dto.ContentDTO;
import com.br.simulatedV2.dto.QuestionDTO;
import com.br.simulatedV2.models.Answer;
import com.br.simulatedV2.models.Content;
import com.br.simulatedV2.models.Question;
import com.br.simulatedV2.service.AnswerService;
import com.br.simulatedV2.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/questions")
@AllArgsConstructor
public class QuestionController {

    QuestionService questionService;
    AnswerService answerService;

    @GetMapping("")
    public ModelAndView getList(){
        ModelAndView mv = new ModelAndView("/questions/index");
        List<Question>questions = questionService.findAll();
        mv.addObject("questions", questions);
        return mv;
    }

    //chamar o form de criação de uma questão
    @GetMapping("/new")
    public ModelAndView newQuest(QuestionDTO questionDTO){
        ModelAndView mv = new ModelAndView("/questions/new");
        mv.addObject("type", Type.values());
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView findAnswers(@PathVariable Long id){
        ModelAndView mv = new ModelAndView("/questions/responsePage");
        List<Answer> answerList = answerService.findAllByQuestion(id);
        if(answerList.isEmpty()){
            return new ModelAndView("redirect:/questions");
        }
        mv.addObject("answers", answerList);
        mv.addObject("type", Type.values());
        return mv;
    }

    @PostMapping("")
    public ModelAndView create(@Valid QuestionDTO obj, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("/questions/new");
            mv.addObject("type", Type.values());
            return mv;
        }else {
            Question question = obj.toQuestion();
            questionService.create(question);
            return new ModelAndView("redirect:/questions");
        }
    }

    @PostMapping("/{id}")
    public String responsePage0Post(@PathVariable(name = "id")Long id, Answer answer){
        Question question = questionService.findById(id);
        answer.setQuestion(question);
        answerService.create(answer);
        return "redirect:/contents";
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, QuestionDTO obj){
        Question question = questionService.findById(id);
        if(question != null){
            obj.fromQuestion(question);
            ModelAndView mv = new ModelAndView("/questions/edit");
            mv.addObject("id", question.getId());
            mv.addObject("type", Type.values());
            return mv;
        }else {
            ModelAndView mv = new ModelAndView("redirect:/questions");
            return mv;
        }
    }

    @PostMapping("/{id}/edit")
    public ModelAndView update(@PathVariable Long id, @Valid QuestionDTO obj, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("/questions/edit");
            return mv;
        }else {
            Optional<Question> optional = questionService.returnOptional(id);
            if(optional.isPresent()){
                Question question = obj.toQuestion(optional.get());
                questionService.create(question);
                return new ModelAndView("redirect:/questions");
            }else {
                ModelAndView mv = new ModelAndView("redirect:/questions");
                return mv;
            }
        }
    }


    //Request para deletar uma question
    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id){
        ModelAndView mv = new ModelAndView("redirect:/questions");
        try {
            questionService.delete(id);
        }catch (EmptyResultDataAccessException e){
            return mv;
        }
        return mv;
    }




}
