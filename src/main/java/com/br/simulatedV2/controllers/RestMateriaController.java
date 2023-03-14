package com.br.simulatedV2.controllers;

import com.br.simulatedV2.dto.MateriaDTO;
import com.br.simulatedV2.models.Materia;
import com.br.simulatedV2.service.MateriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/back")
public class RestMateriaController {

    MateriaService materiaService;

    //REQUEST ALL MATERIALS
    @GetMapping("/all")
    public ResponseEntity<List<MateriaDTO>> getAll(){
        ModelAndView mv = new ModelAndView("/materias/index");
        List<Materia> materiaList = materiaService.findAll();
        List<MateriaDTO> dtoList = materiaList.stream().map(obj -> new MateriaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }

    //REQUEST FOR SEARCH MATERIA BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Materia>findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(materiaService.findById(id));
    }

    @PostMapping("/materias")
    public ResponseEntity<Materia>create(@Valid @RequestBody Materia materia){
        return ResponseEntity.status(HttpStatus.CREATED).body(materiaService.create(materia));
    }

    //REQUEST FOR REPLACE A MATERIA

    @PatchMapping("/replace/{id}")
    public ResponseEntity<MateriaDTO>replace(@Valid @PathVariable Long id, @RequestBody MateriaDTO materiaDTO){
        Materia newObj = materiaService.replace(id, materiaDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new MateriaDTO(newObj));
    }

    //REQUEST FOR UPDATE A MATERIA
    @PutMapping("/{id}")
    public ResponseEntity<MateriaDTO>update(@Valid @PathVariable Long id, @RequestBody MateriaDTO materiaDTO){
        Materia newObj = materiaService.replace(id, materiaDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new MateriaDTO(newObj));
    }

    //REQUEST FOR DELETE A MATERIA
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void>delete(@PathVariable Long id){
        materiaService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
