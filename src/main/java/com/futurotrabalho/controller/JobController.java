package com.futurotrabalho.controller;

import com.futurotrabalho.model.dto.JobResponseDTO;
import com.futurotrabalho.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
@Tag(name = "Jobs", description = "Acompanhamento de jobs assíncronos")
public class JobController {
    
    private final JobService jobService;
    
    @GetMapping("/{jobId}")
    @Operation(summary = "Obter status do job", description = "Retorna o status atual de um job de processamento")
    public ResponseEntity<JobResponseDTO> findByJobId(@PathVariable String jobId) {
        JobResponseDTO job = jobService.findByJobId(jobId);
        return ResponseEntity.ok(job);
    }
}

