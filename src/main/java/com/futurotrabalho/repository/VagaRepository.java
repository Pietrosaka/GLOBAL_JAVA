package com.futurotrabalho.repository;

import com.futurotrabalho.model.entity.Skill;
import com.futurotrabalho.model.entity.Vaga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    Page<Vaga> findByAtivaTrue(Pageable pageable);
    
    Page<Vaga> findByTipoTrabalhoAndAtivaTrue(Vaga.TipoTrabalho tipoTrabalho, Pageable pageable);
    
    @Query("SELECT DISTINCT v FROM Vaga v " +
           "JOIN v.skillsRequeridas s " +
           "WHERE s.id IN :skillIds AND v.ativa = true")
    Page<Vaga> findBySkillsRequeridas(@Param("skillIds") Set<Long> skillIds, Pageable pageable);
    
    @Query("SELECT DISTINCT v FROM Vaga v " +
           "JOIN v.skillsRequeridas s " +
           "WHERE s.nome LIKE %:skillName% AND v.ativa = true")
    Page<Vaga> findBySkillName(@Param("skillName") String skillName, Pageable pageable);
}

