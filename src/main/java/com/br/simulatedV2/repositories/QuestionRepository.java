package com.br.simulatedV2.repositories;

import com.br.simulatedV2.models.Materia;
import com.br.simulatedV2.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select q from Question as q where q.statement like %?1% ")
    List<Question> findQuestionByStatement(String statement);
}
