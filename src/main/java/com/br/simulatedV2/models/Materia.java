package com.br.simulatedV2.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String name;
    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "materia")
    private List<Content> contents = new ArrayList<>();

    public Materia(Long id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
