package com.br.simulatedV2.models;

import com.br.simulatedV2.Enum.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;
    @NotEmpty(message = "THE FIELD STATEMENT IS REQUIRED")
    private String response;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;

    public void fromAnswer(Answer answer){
        this.response = answer.getResponse();
        this.type = answer.getType();
    }


}
