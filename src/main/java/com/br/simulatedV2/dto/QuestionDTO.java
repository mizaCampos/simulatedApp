package com.br.simulatedV2.dto;

import com.br.simulatedV2.Enum.Type;
import com.br.simulatedV2.models.Question;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.servlet.http.PushBuilder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class QuestionDTO {

    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;
    @NotBlank
    @NotNull
    @NotEmpty(message = "THE FIELD STATEMENT IS REQUIRED")
    private String statement;

    public QuestionDTO(Question question){
        QuestionDTO obj = new QuestionDTO();
        obj.setType(question.getType());
        obj.setStatement(question.getStatement());
    }

    public Question toQuestion(){
        Question question = new Question();
        question.setType(this.getType());
        question.setStatement(this.getStatement());
        return question;
    }

    //Question from QuestionDTO
    public void fromQuestion(Question question){
        this.type = question.getType();
        this.statement = question.getStatement();
    }

    //recebe os dados de um questionDto e retorna uma question
    public Question toQuestion(Question question){
        question.setType(this.getType());
        question.setStatement(this.getStatement());
        return question;
    }




}
