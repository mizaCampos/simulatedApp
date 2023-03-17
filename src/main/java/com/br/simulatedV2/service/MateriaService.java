package com.br.simulatedV2.service;

import com.br.simulatedV2.dto.MateriaDTO;
import com.br.simulatedV2.exceptions.exceptionsClass.ObjectNotFoundException;
import com.br.simulatedV2.models.Content;
import com.br.simulatedV2.models.Materia;
import com.br.simulatedV2.repositories.MateriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MateriaService {
    private MateriaRepository materiaRepository;

    public Materia findById(Long id){
        Optional<Materia> obj = materiaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
    }

    public List<Materia>findAll(){
        return materiaRepository.findAll();
    }

    public Materia create(Materia materia){
        return materiaRepository.save(materia);
    }

    public Materia replace(Long id, MateriaDTO materiaDTO) {
        Materia obj = findById(id);
        obj.setName(materiaDTO.getName());
        obj.setDescription(materiaDTO.getDescription());
        return materiaRepository.save(obj);
    }

    public void delete(Long id){
        materiaRepository.deleteById(id);
    }

    public List<Materia> findMateriaByName(String name){
        List<Materia> materias =  materiaRepository.findMateriaByName(name);
        return materias;
    }

//    public void delete(Long id){
//        try {
//            materiaRepository.deleteById(id);
//        }catch (DataIntegrityViolationException e){
//            throw new com.br.simulatedV2.exceptions.exceptionsClass.DataIntegrityViolationException("O objeto não pode ser" +
//                    "deletado pois possui outros objetos associados a ele");
//        }
//    }

    public Optional<Materia>objOptional(Long id){
        Optional<Materia> obj = materiaRepository.findById(id);
        return obj;
    }
}
