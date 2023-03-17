package com.br.simulatedV2.controllers.front;

import com.br.simulatedV2.Enum.Type;
import com.br.simulatedV2.dto.ContentDTO;
import com.br.simulatedV2.models.Answer;
import com.br.simulatedV2.models.Content;
import com.br.simulatedV2.models.Question;
import com.br.simulatedV2.service.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.MethodOverride;
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

@Controller
@RequestMapping("/answers")
@AllArgsConstructor
public class AnswerController {

    AnswerService answerService;

    @GetMapping("")
    public ModelAndView getList(){
        List<Answer>answersList =  answerService.findAll();
        ModelAndView mv = new ModelAndView("answers/index");
        mv.addObject("answers", answersList);
        return mv;
    }

    //método que chama o form de criacao de uma answer
    @GetMapping("/new")
    public ModelAndView newAnswer(Answer answer){
        ModelAndView mv = new ModelAndView("/answers/new");
        mv.addObject("type", Type.values());
        return mv;
    }
    //metodo que recebe o form do /new e cria um objeto answer
    @PostMapping("")
    public ModelAndView create(@Valid Answer obj, BindingResult bindingResult){
        //verificando os campos
        if(bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("/answers/new");
            return mv;
        }else {
            Answer answer = obj;
            answerService.create(answer);
            return new ModelAndView("redirect:/answers");
        }
    }
    //request para editar uma pergunta
    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, Answer obj){
        Answer answer = answerService.findById(id);
        if(answer != null){
            //passando os valores dos atributos vindo do objeto da requisação para dentro do novo objeto;
            obj.fromAnswer(answer);
            ModelAndView mv = new ModelAndView("/answers/edit");
            mv.addObject("type", Type.values());
            mv.addObject("id", answer.getId());
            return mv;
        }else {
            ModelAndView mv = new ModelAndView("redirect:/answers");
            return mv;
        }
    }


    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid Answer obj, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("/answers/edit");
            return mv;
        }else {
            Optional<Answer> optional = answerService.returnOptional(id);
            if (optional.isPresent()){
                Answer answer = obj;
                answerService.create(answer);
                return new ModelAndView("redirect:/answers");
            }else {
                ModelAndView mv = new ModelAndView("redirect:/answers");
                return mv;
            }
        }
    }

    //request para deletar uma questao
    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id){
        ModelAndView mv = new ModelAndView("redirect:/answers");
        try {
            answerService.delete(id);
        }catch (EmptyResultDataAccessException e){
            return mv;
        }
        return mv;
    }

    //Metodo para tratar erros
    private ModelAndView retornaErroMateria(String msg){
        ModelAndView mv = new ModelAndView("redirect:/materias");
        mv.addObject("mensagem", msg);
        mv.addObject("erro", true);
        return mv;
    }


}
