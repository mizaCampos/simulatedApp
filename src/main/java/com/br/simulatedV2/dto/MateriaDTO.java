package com.br.simulatedV2.dto;

import com.br.simulatedV2.models.Materia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaDTO {

    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String description;

    public Materia ToMateria(){
        Materia materia = new Materia();
        materia.setName(this.getName());
        materia.setDescription(this.getDescription());
        return materia;
    }

    public Materia toMateria(Materia materia){
        materia.setName(this.getName());
        materia.setDescription(this.getDescription());
        return materia;
    }

    public MateriaDTO(Materia materia){
        super();
        this.name = materia.getName();
        this.description = materia.getDescription();
    }

    public void fromMateria(Materia materia){
        this.name = materia.getName();
        this.description = materia.getDescription();
    }


}
