package com.br.simulatedV2.controllers;

import com.br.simulatedV2.dto.ContentDTO;
import com.br.simulatedV2.models.Content;
import com.br.simulatedV2.service.ContentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/back")
public class RestContentController {

    private ContentService contentService;

    @GetMapping("/findById/{id}")
    public ResponseEntity<ContentDTO> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(new ContentDTO(contentService.findById(id)));
    }

    @GetMapping("/contents/all")
    public ResponseEntity<List<ContentDTO>>findAll(){
        List<Content> list = contentService.findAll();
        List<ContentDTO> listDTO = list.stream().map(obj -> new ContentDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(listDTO);
    }

    @GetMapping("/contents/findAll")
    public ResponseEntity<List<Content>>getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(contentService.findAll());
    }

    @GetMapping()
    public ResponseEntity<List<ContentDTO>>getAllByMateria(@RequestParam(value = "materia", defaultValue = "0")Long id){
        List<Content> list = contentService.findAllByMateria(id);
        List<ContentDTO>listDTO = list.stream().map(obj -> new ContentDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(listDTO);
    }

    @PostMapping("/content")
    public ResponseEntity<Content>create(@RequestParam (name = "materia", required = false)Long id, @RequestBody @Valid Content objContent){
        return ResponseEntity.status(HttpStatus.CREATED).body(contentService.createWithMateria(id, objContent));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Content>update(@PathVariable Long id, @RequestBody ContentDTO contentDTO){
        return ResponseEntity.status(HttpStatus.OK).body(contentService.replace(id, contentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@PathVariable Long id){
        contentService.delete(id);
        return ResponseEntity.noContent().build();
    }








}
