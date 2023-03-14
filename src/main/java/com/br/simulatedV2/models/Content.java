package com.br.simulatedV2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotEmpty(message = "FIELD NAME IS REQUIRED")
    private String name;

    @NotEmpty(message = "FIELD DESCRIPTION IS REQUIRED")
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "materia_id", referencedColumnName = "id")
    private Materia materia;

    @OneToMany(mappedBy = "content")
    private List<Question> questions = new ArrayList<>();

}
