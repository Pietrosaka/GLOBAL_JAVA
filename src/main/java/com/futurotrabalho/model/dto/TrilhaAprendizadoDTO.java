package com.futurotrabalho.model.dto;

import com.futurotrabalho.model.entity.TrilhaAprendizado;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class TrilhaAprendizadoDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String conteudoGeradoAI;
    private Long usuarioId;
    private TrilhaAprendizado.StatusTrilha status;
    private Integer duracaoEstimadaHoras;
    private TrilhaAprendizado.NivelTrilha nivel;
    private LocalDateTime createdAt;
    private Set<SkillDTO> skills;
}

