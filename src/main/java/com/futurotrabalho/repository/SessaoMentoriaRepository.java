package com.futurotrabalho.repository;

import com.futurotrabalho.model.entity.SessaoMentoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoMentoriaRepository extends JpaRepository<SessaoMentoria, Long> {
    Page<SessaoMentoria> findByUsuarioId(Long usuarioId, Pageable pageable);
}

