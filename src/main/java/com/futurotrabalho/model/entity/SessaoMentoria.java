package com.futurotrabalho.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sessoes_mentoria")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessaoMentoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @Column(name = "tipo_sessao", nullable = false, length = 50)
    private String tipoSessao; // "SIMULACAO_ENTREVISTA", "FEEDBACK_CARREIRA", etc.
    
    @Column(name = "pergunta", length = 1000)
    private String pergunta;
    
    @Column(name = "resposta", columnDefinition = "TEXT")
    private String resposta;
    
    @Column(name = "feedback_ai", columnDefinition = "TEXT")
    private String feedbackAI;
    
    @Column(name = "score")
    private Integer score; // 0-100
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

