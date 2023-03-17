package com.br.simulatedV2.service;


import com.br.simulatedV2.dto.QuestionDTO;
import com.br.simulatedV2.exceptions.exceptionsClass.ObjectNotFoundException;
import com.br.simulatedV2.models.Content;
import com.br.simulatedV2.models.Question;
import com.br.simulatedV2.repositories.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionService {
    List<Question>questions = new ArrayList<>();
    QuestionRepository questionRepository;
    ContentService contentService;

    //metodo que busca por id e retorna um objeto do tipo question, ou um tratamento de execption
    public Question findById(Long id){
        Optional<Question>obj = questionRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
    }

    //metodo que retorna um optional de question
    public Optional<Question>returnOptional(Long id){
        return questionRepository.findById(id);

    }
    //get all questions
    public List<Question>findAll(){
        return questionRepository.findAll();
    }

        public List<Question>findAllByContent(Long id){
        Content content = contentService.findById(id);
        List<Question>questions = content.getQuestions();
        return questions;
    }


    //create a new questions
    public Question create(Question question){
        return questionRepository.save(question);
    }

    public Question createWithContent(Long id, Question objQuestion){
        Content content = contentService.findById(id);
        objQuestion.setContent(content);
        return questionRepository.save(objQuestion);
    }

    //metodo para atualizar uma question
    public Question replace(QuestionDTO obj, Long id){
        Question question = findById(id);
        question.setStatement(question.getStatement());
        question.setType(question.getType());
        return questionRepository.save(question);
    }

    public List<Question>findQuestionByStatement(String name){
        return questionRepository.findQuestionByStatement(name);
    }

    public void deleteFront(Long id){
        questionRepository.deleteById(id);
    }

    public void delete(Long id){
        try {
            questionRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new com.br.simulatedV2.exceptions.exceptionsClass.DataIntegrityViolationException("O objeto não pode ser" +
                    "deletado pois possui outros objetos associados a ele");
        }
    }



}
