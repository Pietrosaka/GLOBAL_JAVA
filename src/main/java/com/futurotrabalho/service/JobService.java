package com.futurotrabalho.service;

import com.futurotrabalho.exception.ResourceNotFoundException;
import com.futurotrabalho.model.dto.JobResponseDTO;
import com.futurotrabalho.model.entity.JobProcessamento;
import com.futurotrabalho.repository.JobProcessamentoRepository;
import com.futurotrabalho.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobService {
    
    private final JobProcessamentoRepository jobRepository;
    private final EntityMapper mapper;
    
    @Transactional(readOnly = true)
    public JobResponseDTO findByJobId(String jobId) {
        JobProcessamento job = jobRepository.findByJobId(jobId)
            .orElseThrow(() -> new ResourceNotFoundException("JobProcessamento", jobId));
        return mapper.toJobResponseDTO(job);
    }
}

