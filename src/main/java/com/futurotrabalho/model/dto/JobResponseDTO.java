package com.futurotrabalho.model.dto;

import com.futurotrabalho.model.entity.JobProcessamento;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobResponseDTO {
    private String jobId;
    private String tipoJob;
    private JobProcessamento.StatusJob status;
    private String resultado;
    private String erro;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

