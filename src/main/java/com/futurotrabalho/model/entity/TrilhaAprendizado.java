package com.futurotrabalho.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trilhas_aprendizado")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrilhaAprendizado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String titulo;
    
    @Column(nullable = false, length = 2000)
    private String descricao;
    
    @Column(name = "conteudo_gerado_ai", columnDefinition = "TEXT")
    private String conteudoGeradoAI;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTrilha status;
    
    @Column(name = "duracao_estimada_horas")
    private Integer duracaoEstimadaHoras;
    
    @Column(name = "nivel")
    @Enumerated(EnumType.STRING)
    private NivelTrilha nivel;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "trilha_skills",
        joinColumns = @JoinColumn(name = "trilha_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = StatusTrilha.PENDENTE;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum StatusTrilha {
        PENDENTE,
        EM_PROGRESSO,
        CONCLUIDA,
        CANCELADA
    }
    
    public enum NivelTrilha {
        INICIANTE,
        INTERMEDIARIO,
        AVANCADO
    }
}

