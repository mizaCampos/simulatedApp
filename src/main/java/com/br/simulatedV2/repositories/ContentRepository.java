package com.br.simulatedV2.repositories;

import com.br.simulatedV2.models.Content;
import com.br.simulatedV2.models.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query("select c from Content as c where c.name like %?1% ")
    List<Content> findContentByName(String name);
}
