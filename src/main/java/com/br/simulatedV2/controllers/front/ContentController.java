package com.br.simulatedV2.controllers.front;

import com.br.simulatedV2.Enum.Type;
import com.br.simulatedV2.dto.ContentDTO;
import com.br.simulatedV2.models.Content;
import com.br.simulatedV2.models.Materia;
import com.br.simulatedV2.models.Question;
import com.br.simulatedV2.service.ContentService;
import com.br.simulatedV2.service.MateriaService;
import com.br.simulatedV2.service.QuestionService;
import lombok.AllArgsConstructor;

import org.springframework.boot.Banner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/contents")
@AllArgsConstructor
public class ContentController {

    ContentService contentService;
    MateriaService materiaService;
    QuestionService questionService;
    MateriaController materiaController;


    //REQUEST FOR GET ALL CONTENTS
    @GetMapping("")
    public ModelAndView getList(){
        List<Content>contents = contentService.findAll();

        ModelAndView mv = new ModelAndView("/contents/index");
        mv.addObject("contents", contents);
        return mv;
    }


    //metodo que chamar o form de criação de um content
    @GetMapping("/new")
    public ModelAndView newContent(ContentDTO contentDTO){
        ModelAndView mv = new ModelAndView("/contents/new");
        return mv;
    }

    //request que retorna uma lista de question pelo id do content
    @GetMapping("/{id}")
    public ModelAndView findQuestions(@PathVariable Long id){
        ModelAndView mv = new ModelAndView("/contents/questionsPage");
        //pegando a lista de questions pelo o id de um conteudo na url
        List<Question> questionsList = questionService.findAllByContent(id);
        if(questionsList.isEmpty()){
            System.out.println("Esta vazia");
        }
        mv.addObject("questions", questionsList);
        mv.addObject("type", Type.values());
        return mv;
    }


    @PostMapping("")
    public ModelAndView create(@Valid ContentDTO obj, BindingResult bindingResult){

        //verificando a validade dos campos
        if (bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("/contents/new");
            return mv;
        }else {
            Content content = obj.toContent();
            contentService.create(content);
            return new ModelAndView("redirect:/contents");

        }
    }

    //criar uma Question apartir do formulario da pagina questionsPagePost
    @PostMapping("/{id}")
    public String questionsPagePost(@PathVariable(name = "id")Long id, Question question){
        Content content = contentService.findById(id);
        question.setContent(content);
        questionService.create(question);
        return "redirect:/contents";
    }


    //request para editar um conteudo
    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, ContentDTO obj){
        Content content = contentService.findById(id);
        if(content != null){
            obj.fromContent(content);
            ModelAndView mv = new ModelAndView("/contents/edit");
            mv.addObject("id", content.getId());
            return mv;
        }else {
            ModelAndView mv = new ModelAndView("redirect:/contents");
            return mv;
        }
    }

    //metodo do componente de pesquisar da pagina
    @PostMapping("/pesquisarcontents")
    public ModelAndView pesquisarByName(@RequestParam("nomepesquisa")String nomepesquisa){
        ModelAndView mv = new ModelAndView("/contents/index");
        mv.addObject("contents",contentService.findContentByName(nomepesquisa));
        mv.addObject("contentobj", new Content());
        return mv;
    }

    //request que recebe o form de edição de um content /{id}/edit
    @PostMapping("/{id}/edit")
    public ModelAndView update(@PathVariable Long id, @Valid ContentDTO obj, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("/contents/edit");
            return mv;
        }else {
            Optional<Content> optional = contentService.objOptional(id);
            if(optional.isPresent()){

                Materia materia = materiaService.findById(id);
                Content content = obj.toContent(optional.get());
                content.setMateria(materia);
                contentService.create(content);
                return new ModelAndView("redirect:/contents");
            }else {
                ModelAndView mv = new ModelAndView("redirect:/contents");
                return mv;
            }
        }
    }


    //Request para deletar um conteudo
    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id){
        ModelAndView mv = new ModelAndView("redirect:/contents");
        try {
            contentService.deleteFront(id);
            mv.addObject("mensagem", "Conteúdo deletado com sucesso");
            mv.addObject("erro", false);
        }catch (DataIntegrityViolationException e){
            mv = this.retornaErroMateria("Erro em deletar o conteudo de ID " + id + " pois contém questões relacionadas a ele");
        }
        return mv;
    }


    private ModelAndView retornaErroMateria(String msg){
        ModelAndView mv = new ModelAndView("redirect:/contents");
        mv.addObject("mensagem", msg);
        mv.addObject("erro", true);
        return mv;
    }


}
