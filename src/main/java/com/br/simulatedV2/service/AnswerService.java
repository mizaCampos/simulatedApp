package com.br.simulatedV2.service;

import com.br.simulatedV2.exceptions.exceptionsClass.DataIntegrityViolationException;
import com.br.simulatedV2.exceptions.exceptionsClass.ObjectNotFoundException;
import com.br.simulatedV2.models.Answer;
import com.br.simulatedV2.models.Question;
import com.br.simulatedV2.repositories.AnswersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnswerService {

    AnswersRepository answersRepository;
    QuestionService questionService;

    //metodo que busca por id e retorna uma answer
    public Answer findById(Long id){
        Optional<Answer> obj = answersRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    //metodo que busca por id e retorna um optional de answer
    public Optional<Answer>returnOptional(Long id){
        return answersRepository.findById(id);
    }

    //metodo que busca todos registros de answer

    public List<Answer>findAll(){
        return answersRepository.findAll();
    }

    //metodo que busca answers de uma determinada questão
    public List<Answer> findAllByQuestion(Long id){
        Question question = questionService.findById(id);
        return question.getAnswers();
    }

    //metodo para criar uma answer
    public Answer create(Answer answer){

        return answersRepository.save(answer);
    }

    //metodo para criar uma answer
    public Answer createWithQuestion(Long id, Answer answer){
        Question question = questionService.findById(id);
        answer.setQuestion(question);
        return answersRepository.save(answer);
    }

    //metodo para atualizar uma answer
    public Answer replace(Long id, Answer obj){
        Answer answer = findById(id);
        answer.setType(obj.getType());
        answer.setResponse(obj.getResponse());
        return answersRepository.save(answer);
    }

    public void delete(Long id){
        try {
            answersRepository.deleteById(id);
        }catch (org.springframework.dao.DataIntegrityViolationException e){
            throw new com.br.simulatedV2.exceptions.exceptionsClass.DataIntegrityViolationException("O objeto não pode ser" +
                    "deletado pois possui outros objetos associados a ele");
        }
    }



}
