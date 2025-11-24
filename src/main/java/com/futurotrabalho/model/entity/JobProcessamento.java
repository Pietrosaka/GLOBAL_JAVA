package com.futurotrabalho.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs_processamento")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobProcessamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "job_id", nullable = false, unique = true, length = 100)
    private String jobId;
    
    @Column(name = "tipo_job", nullable = false, length = 50)
    private String tipoJob; // "GERAR_TRILHA", "PROCESSAR_RELATORIO", "ENVIAR_EMAIL"
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusJob status;
    
    @Column(name = "usuario_id")
    private Long usuarioId;
    
    @Column(name = "dados_entrada", columnDefinition = "TEXT")
    private String dadosEntrada;
    
    @Column(name = "resultado", columnDefinition = "TEXT")
    private String resultado;
    
    @Column(name = "erro", columnDefinition = "TEXT")
    private String erro;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = StatusJob.PENDENTE;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum StatusJob {
        PENDENTE,
        PROCESSANDO,
        CONCLUIDO,
        FALHA
    }
}

