package com.br.simulatedV2.models;

import com.br.simulatedV2.Enum.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;
    @NotEmpty(message = "THE FIELD STATEMENT IS REQUIRED")
    private String statement;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "content_id", referencedColumnName = "id")
    private Content content;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();


}
