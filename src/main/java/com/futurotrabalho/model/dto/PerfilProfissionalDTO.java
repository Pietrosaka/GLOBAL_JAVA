package com.futurotrabalho.model.dto;

import com.futurotrabalho.model.entity.PerfilProfissional;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class PerfilProfissionalDTO {
    private Long id;
    private Long usuarioId;
    private String resumo;
    private Integer experienciaAnos;
    private LocalDate dataNascimento;
    private String cidade;
    private String estado;
    private String pais;
    private PerfilProfissional.PreferenciaTrabalho preferenciaTrabalho;
    private Double pretensaoSalarial;
    private Set<SkillDTO> skills;
}

