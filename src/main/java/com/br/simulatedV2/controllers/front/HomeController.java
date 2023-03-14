package com.br.simulatedV2.controllers.front;

import com.br.simulatedV2.models.Content;
import com.br.simulatedV2.models.Materia;
import com.br.simulatedV2.service.ContentService;
import com.br.simulatedV2.service.MateriaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    MateriaService materiaService;
    ContentService contentService;
    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/relacao")
    public ModelAndView relacao(){
        ModelAndView mv = new ModelAndView("relacao");
        List<Materia>materiaList = materiaService.findAll();
        List<Content>contentList = contentService.findAll();
        mv.addObject("content", contentList);
        return mv;
    }


}
