package com.br.simulatedV2.service;

import com.br.simulatedV2.dto.ContentDTO;
import com.br.simulatedV2.dto.MateriaDTO;
import com.br.simulatedV2.exceptions.exceptionsClass.ObjectNotFoundException;
import com.br.simulatedV2.models.Content;
import com.br.simulatedV2.models.Materia;
import com.br.simulatedV2.repositories.ContentRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContentService {
    ContentRepository contentRepository;
    MateriaService materiaService;

    public Content findById(Long id) {
        Optional<Content> obj = contentRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
    }

    public Optional<Content>objOptional(Long id){
        return contentRepository.findById(id);
    }

    public List<Content>findAll(){
        return contentRepository.findAll();
    }

    public List<Content> findAllByMateria(Long id) {
        Materia materia = materiaService.findById(id);
        List<Content> contents = materia.getContents();
        return contents;
    }

    public Content createWithMateria(Long id, Content objContent){
        Materia materia = materiaService.findById(id);
        objContent.setMateria(materia);
        return contentRepository.save(objContent);
    }

    public Content create(Content content){
        return contentRepository.save(content);
    }

    public List<Content>findContentByName(String name){
        return contentRepository.findContentByName(name);
    }

    public Content replace(Long id, ContentDTO contentDTO){
        Content content = findById(id);
        content.setName(contentDTO.getName());
        content.setDescription(contentDTO.getDescription());
        return contentRepository.save(content);
    }

    public void deleteFront(Long id){
        contentRepository.deleteById(id);
    }

    public void delete(Long id){
        try {
            contentRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new com.br.simulatedV2.exceptions.exceptionsClass.DataIntegrityViolationException("O objeto não pode ser" +
                    "deletado pois possui outros objetos associados a ele");
        }
    }

}
