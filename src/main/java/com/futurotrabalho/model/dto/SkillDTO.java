package com.futurotrabalho.model.dto;

import com.futurotrabalho.model.entity.Skill;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SkillDTO {
    private Long id;
    
    @NotBlank(message = "{validation.skill.nome.required}")
    private String nome;
    
    private String descricao;
    private Skill.CategoriaSkill categoria;
    private Integer demandaMercado;
}

