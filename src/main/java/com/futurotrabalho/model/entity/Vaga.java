package com.futurotrabalho.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vagas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vaga {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String titulo;
    
    @Column(nullable = false, length = 2000)
    private String descricao;
    
    @ManyToOne
    @JoinColumn(name = "recrutador_id", nullable = false)
    private Usuario recrutador;
    
    @Column(length = 100)
    private String empresa;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_trabalho", nullable = false)
    private TipoTrabalho tipoTrabalho;
    
    @Column(name = "salario_min")
    private BigDecimal salarioMin;
    
    @Column(name = "salario_max")
    private BigDecimal salarioMax;
    
    @Column(length = 100)
    private String localizacao;
    
    @Column(nullable = false)
    private Boolean ativa;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "vaga_skills",
        joinColumns = @JoinColumn(name = "vaga_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skillsRequeridas = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (ativa == null) {
            ativa = true;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum TipoTrabalho {
        REMOTO,
        HIBRIDO,
        PRESENCIAL
    }
}

