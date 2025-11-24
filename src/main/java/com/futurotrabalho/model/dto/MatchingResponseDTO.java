package com.futurotrabalho.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchingResponseDTO {
    private VagaDTO vaga;
    private Double scoreCompatibilidade; // 0-100
    private Integer skillsMatch;
    private Integer totalSkillsRequeridas;
    private String justificativa;
}

