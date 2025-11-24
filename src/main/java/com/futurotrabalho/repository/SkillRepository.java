package com.futurotrabalho.repository;

import com.futurotrabalho.model.entity.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findByNome(String nome);
    List<Skill> findByNomeContainingIgnoreCase(String nome);
    
    @Query("SELECT s FROM Skill s WHERE s.id IN :ids")
    Set<Skill> findByIds(@Param("ids") Set<Long> ids);
    
    Page<Skill> findByCategoria(Skill.CategoriaSkill categoria, Pageable pageable);
    
    @Query("SELECT s FROM Skill s ORDER BY s.demandaMercado DESC NULLS LAST")
    Page<Skill> findTopSkillsByDemanda(Pageable pageable);
}

