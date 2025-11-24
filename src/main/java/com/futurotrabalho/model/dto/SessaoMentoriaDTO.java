package com.futurotrabalho.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessaoMentoriaDTO {
    private Long id;
    
    @NotBlank
    private String tipoSessao;
    
    private String pergunta;
    private String resposta;
    private String feedbackAI;
    private Integer score;
    private LocalDateTime createdAt;
}

