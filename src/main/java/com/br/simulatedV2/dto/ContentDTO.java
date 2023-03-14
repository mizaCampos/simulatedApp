package com.br.simulatedV2.dto;

import com.br.simulatedV2.models.Content;
import com.br.simulatedV2.models.Materia;
import com.br.simulatedV2.models.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContentDTO {
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String description;

    @OneToMany(mappedBy = "content")
    private List<Question>questions = new ArrayList<>();

    public ContentDTO(Content content){
        this.name = content.getName();
        this.description = content.getDescription();
    }

    //Gera e retorna um content com os dados de um Content dto
    public Content toContent(){
        Content content = new Content();
        content.setDescription(this.getDescription());
        content.setName(this.getName());
        return content;
    }

    //Content para ContentDTO
    public void fromContent(Content content){
        this.name = content.getName();
        this.description = content.getDescription();
    }

    //recebe um content por parametro e converte para dto
    public Content toContent(Content content){
        content.setName(this.getName());
        content.setDescription(this.getDescription());
        return content;
    }

}
