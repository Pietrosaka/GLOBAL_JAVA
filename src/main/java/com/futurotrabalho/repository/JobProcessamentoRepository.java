package com.futurotrabalho.repository;

import com.futurotrabalho.model.entity.JobProcessamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobProcessamentoRepository extends JpaRepository<JobProcessamento, Long> {
    Optional<JobProcessamento> findByJobId(String jobId);
}

