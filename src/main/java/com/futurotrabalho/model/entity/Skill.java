package com.futurotrabalho.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skills")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String nome;
    
    @Column(length = 500)
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaSkill categoria;
    
    @Column(name = "demanda_mercado")
    private Integer demandaMercado; // 0-100
    
    @ManyToMany(mappedBy = "skills")
    private Set<PerfilProfissional> perfis = new HashSet<>();
    
    @ManyToMany(mappedBy = "skillsRequeridas")
    private Set<Vaga> vagas = new HashSet<>();
    
    @ManyToMany(mappedBy = "skills")
    private Set<TrilhaAprendizado> trilhas = new HashSet<>();
    
    public enum CategoriaSkill {
        TECNICA,
        COMPORTAMENTAL,
        LINGUAGEM,
        FERRAMENTA,
        METODOLOGIA
    }
}

