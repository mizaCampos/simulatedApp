package com.br.simulatedV2.repositories;

import com.br.simulatedV2.models.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    @Query("select m from Materia as m where m.name like %?1% ")
    List<Materia> findMateriaByName(String name);
}
