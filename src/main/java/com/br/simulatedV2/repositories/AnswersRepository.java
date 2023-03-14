package com.br.simulatedV2.repositories;

import com.br.simulatedV2.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswersRepository extends JpaRepository<Answer, Long> {
}
