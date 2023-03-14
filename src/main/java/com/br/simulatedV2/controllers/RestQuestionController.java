package com.br.simulatedV2.controllers;

import com.br.simulatedV2.dto.QuestionDTO;
import com.br.simulatedV2.models.Question;
import com.br.simulatedV2.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/back/questions")
@AllArgsConstructor
public class RestQuestionController {

    QuestionService questionService;

    @GetMapping("")
    ResponseEntity<List<Question>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(questionService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Question>findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(questionService.findById(id));
    }

    @PostMapping("")
    ResponseEntity<Question>create(@RequestParam (name = "content", required = false, defaultValue = "0")Long id, @RequestBody @Valid Question question){
        return ResponseEntity.status(HttpStatus.OK).body(questionService.createWithContent(id,question));
    }

    @PatchMapping("/{id}/update")
    ResponseEntity<Question>update(@RequestBody @Valid QuestionDTO questionDTO, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(questionService.replace(questionDTO, id));
    }

    @PutMapping("/{id}/replace")
    ResponseEntity<Question>replace(@RequestBody @Valid QuestionDTO questionDTO, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(questionService.replace(questionDTO, id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void>delete(@PathVariable Long id){
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }








}
