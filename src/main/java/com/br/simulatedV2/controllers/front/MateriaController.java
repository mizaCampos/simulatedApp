package com.br.simulatedV2.controllers.front;

import com.br.simulatedV2.dto.MateriaDTO;
import com.br.simulatedV2.models.Content;
import com.br.simulatedV2.models.Materia;
import com.br.simulatedV2.service.ContentService;
import com.br.simulatedV2.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
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
//@AllArgsConstructor
@RequestMapping("/materias")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;
    @Autowired
    private ContentService contentService;
    public long materia_id;


    //request para imprimir a lista de materias
    @GetMapping("")
    public ModelAndView getList(){
        List<Materia> materiasList = materiaService.findAll();
        ModelAndView mv = new ModelAndView("/materias/index");
        mv.addObject("materias", materiasList);
        return mv;
    }

    //form de criação de uma nova materia
    @GetMapping("/new")
    public ModelAndView newMateria(MateriaDTO obj){
        ModelAndView mv = new ModelAndView("/materias/new");
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView findContents(@PathVariable Long id){
        ModelAndView mv = new ModelAndView("/materias/detalhes");
        //pegando a lista de conteudos pelo o id de uma materia passado na url
        List<Content>contentList = contentService.findAllByMateria(id);
        mv.addObject("contents", contentList);
        return mv;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, MateriaDTO obj){
        Materia materia = materiaService.findById(id);
        if(materia != null){
            obj.fromMateria(materia);
            ModelAndView mv = new ModelAndView("/materias/edit");
            mv.addObject("id", materia.getId());
            return mv;
        }else {
            ModelAndView mv = new ModelAndView("redirect:/materias");
            return mv = retornaErroMateria("Não foi possivel Atualizar a materia informada");
        }
    }

    @PostMapping("")
    public ModelAndView create(@Valid MateriaDTO obj, BindingResult bindingResult){

        //verificando a validade dos campos
        if (bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("/materias/new");
            return mv;
        }else {
            Materia materia = obj.ToMateria();
            materiaService.create(materia);
            return new ModelAndView("redirect:/materias");
      }
    }

    @PostMapping("/{id}")
    public String detalhesMateriasPost(@PathVariable (name = "id") Long id, Content content){
        materia_id = id;
        Materia materia = materiaService.findById(id);
        content.setMateria(materia);
        contentService.create(content);
        return "redirect:/materias";
    }

    @PostMapping("/{id}/update")
    public ModelAndView update(@PathVariable Long id,@Valid MateriaDTO obj, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("/materias/edit");
            return mv;
        }else {
            Optional<Materia> optional = materiaService.objOptional(id);

            if(optional.isPresent()){
                Materia materia =  obj.toMateria(optional.get());
                materiaService.create(materia);
                return new ModelAndView("redirect:/materias");

            }else {
                ModelAndView mv = new ModelAndView("redirect:/materias");
                return mv = retornaErroMateria("Materia não encontrada");
            }
        }
    }

    //Request para deletar uma materia
    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id){
        ModelAndView mv = new ModelAndView("redirect:/materias");
        try {
            materiaService.delete(id);
            mv.addObject("mensagem", "Materia deletada com sucesso");
            mv.addObject("erro", false);
        }catch (DataIntegrityViolationException e){
           mv = this.retornaErroMateria("Erro em deletar a matéria de id " + id + " pois contém objetos relacionados ");
        }
        return mv;
    }

    @PostMapping("/pesquisarmateria")
    public ModelAndView pesquisar(@RequestParam("nomepesquisa")String nomepesquisa){
        ModelAndView mv = new ModelAndView("/materias/index");
        mv.addObject("materias", materiaService.findMateriaByName(nomepesquisa));
        mv.addObject("materiaobj", new Materia());
        return mv;
    }

    private ModelAndView retornaErroMateria(String msg){
        ModelAndView mv = new ModelAndView("redirect:/materias");
        mv.addObject("mensagem", msg);
        mv.addObject("erro", true);
        return mv;
    }

}
