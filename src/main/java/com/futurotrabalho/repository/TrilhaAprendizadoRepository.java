package com.futurotrabalho.repository;

import com.futurotrabalho.model.entity.TrilhaAprendizado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrilhaAprendizadoRepository extends JpaRepository<TrilhaAprendizado, Long> {
    Page<TrilhaAprendizado> findByUsuarioId(Long usuarioId, Pageable pageable);
    List<TrilhaAprendizado> findByUsuarioIdAndStatus(Long usuarioId, TrilhaAprendizado.StatusTrilha status);
}

