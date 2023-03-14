package com.br.simulatedV2.controllers;

import com.br.simulatedV2.models.Answer;
import com.br.simulatedV2.service.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/back/answers")
public class RestAnswerController {

    AnswerService answerService;

    @GetMapping("")
    public ResponseEntity<List<Answer>>findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(answerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Answer>findById(Long id){
        return ResponseEntity.status(HttpStatus.OK).body(answerService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<Answer>create(@RequestParam (name = "question", required = false) Long id, @RequestBody @Valid Answer answer){
        return ResponseEntity.status(HttpStatus.OK).body(answerService.createWithQuestion(id, answer));
    }

    @PutMapping("/{id}/replace")
    public ResponseEntity<Answer>replace(@PathVariable Long id, @RequestBody Answer answer){
        return ResponseEntity.status(HttpStatus.OK).body(answerService.replace(id, answer));
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Answer>update(@PathVariable Long id, @RequestBody Answer answer){
        return ResponseEntity.status(HttpStatus.OK).body(answerService.replace(id, answer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@PathVariable Long id){
        answerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
