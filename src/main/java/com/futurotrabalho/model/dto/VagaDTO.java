package com.futurotrabalho.model.dto;

import com.futurotrabalho.model.entity.Vaga;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class VagaDTO {
    private Long id;
    
    @NotBlank(message = "{validation.vaga.titulo.required}")
    private String titulo;
    
    @NotBlank(message = "{validation.vaga.descricao.required}")
    private String descricao;
    
    @NotNull
    private Long recrutadorId;
    
    private String empresa;
    
    @NotNull
    private Vaga.TipoTrabalho tipoTrabalho;
    
    private BigDecimal salarioMin;
    private BigDecimal salarioMax;
    private String localizacao;
    private Boolean ativa;
    private LocalDateTime createdAt;
    private Set<SkillDTO> skillsRequeridas;
}

