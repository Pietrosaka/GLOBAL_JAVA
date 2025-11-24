package com.futurotrabalho.repository;

import com.futurotrabalho.model.entity.PerfilProfissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilProfissionalRepository extends JpaRepository<PerfilProfissional, Long> {
    Optional<PerfilProfissional> findByUsuarioId(Long usuarioId);
}

