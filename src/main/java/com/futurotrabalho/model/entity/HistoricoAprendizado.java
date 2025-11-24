package com.futurotrabalho.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_aprendizado")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoAprendizado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "trilha_id")
    private TrilhaAprendizado trilha;
    
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;
    
    @Column(name = "acao", nullable = false, length = 100)
    private String acao; // "INICIOU_TRILHA", "COMPLETOU_CURSO", "ADICIONOU_SKILL"
    
    @Column(name = "descricao", length = 500)
    private String descricao;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

