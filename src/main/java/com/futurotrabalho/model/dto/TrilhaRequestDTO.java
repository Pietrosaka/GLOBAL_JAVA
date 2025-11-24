package com.futurotrabalho.model.dto;

import com.futurotrabalho.model.entity.TrilhaAprendizado;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class TrilhaRequestDTO {
    @NotNull
    private Long usuarioId;
    
    private String objetivo;
    private Set<Long> skillsIds;
    private NivelTrilha nivel;
    private Integer duracaoMaximaHoras;
    
    public enum NivelTrilha {
        INICIANTE,
        INTERMEDIARIO,
        AVANCADO
    }
}

