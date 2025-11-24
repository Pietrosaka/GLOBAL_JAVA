package com.futurotrabalho.service;

import com.futurotrabalho.config.RabbitMQConfig;
import com.futurotrabalho.exception.ResourceNotFoundException;
import com.futurotrabalho.model.dto.JobResponseDTO;
import com.futurotrabalho.model.dto.TrilhaAprendizadoDTO;
import com.futurotrabalho.model.dto.TrilhaRequestDTO;
import com.futurotrabalho.model.entity.JobProcessamento;
import com.futurotrabalho.model.entity.TrilhaAprendizado;
import com.futurotrabalho.model.entity.Usuario;
import com.futurotrabalho.repository.JobProcessamentoRepository;
import com.futurotrabalho.repository.TrilhaAprendizadoRepository;
import com.futurotrabalho.repository.UsuarioRepository;
import com.futurotrabalho.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrilhaService {
    
    private final TrilhaAprendizadoRepository trilhaRepository;
    private final UsuarioRepository usuarioRepository;
    private final JobProcessamentoRepository jobRepository;
    private final RabbitTemplate rabbitTemplate;
    private final EntityMapper mapper;
    private final SpringAIService springAIService;
    
    @Transactional
    public JobResponseDTO solicitarTrilha(TrilhaRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
            .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getUsuarioId()));
        
        String jobId = UUID.randomUUID().toString();
        
        JobProcessamento job = JobProcessamento.builder()
            .jobId(jobId)
            .tipoJob("GERAR_TRILHA")
            .status(JobProcessamento.StatusJob.PENDENTE)
            .usuarioId(usuario.getId())
            .dadosEntrada(request.toString())
            .build();
        
        job = jobRepository.save(job);
        
        // Criar objeto com jobId para enviar para fila
        Map<String, Object> message = new HashMap<>();
        message.put("jobId", jobId);
        message.put("request", request);
        
        // Enviar para fila
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_TRILHAS, message);
        
        log.info("Job {} criado para gerar trilha para usuário {}", jobId, usuario.getId());
        
        return mapper.toJobResponseDTO(job);
    }
    
    @Cacheable(value = "trilhas", key = "#usuarioId + '_' + #pageable.pageNumber")
    @Transactional(readOnly = true)
    public Page<TrilhaAprendizadoDTO> findByUsuario(Long usuarioId, Pageable pageable) {
        return trilhaRepository.findByUsuarioId(usuarioId, pageable)
            .map(mapper::toTrilhaAprendizadoDTO);
    }
    
    @Transactional(readOnly = true)
    public TrilhaAprendizadoDTO findById(Long id) {
        TrilhaAprendizado trilha = trilhaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("TrilhaAprendizado", id));
        return mapper.toTrilhaAprendizadoDTO(trilha);
    }
    
    @Async
    @Transactional
    public void processarTrilha(TrilhaRequestDTO request) {
        try {
            log.info("Processando trilha para usuário {}", request.getUsuarioId());
            
            Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", request.getUsuarioId()));
            
            // Gerar conteúdo da trilha usando Spring AI
            String conteudoAI = springAIService.gerarTrilhaAprendizado(
                request.getObjetivo(),
                request.getNivel(),
                request.getDuracaoMaximaHoras()
            );
            
            TrilhaAprendizado trilha = TrilhaAprendizado.builder()
                .titulo("Trilha Personalizada - " + (request.getObjetivo() != null ? request.getObjetivo() : "Desenvolvimento Profissional"))
                .descricao("Trilha gerada por IA baseada no seu perfil")
                .conteudoGeradoAI(conteudoAI)
                .usuario(usuario)
                .status(TrilhaAprendizado.StatusTrilha.EM_PROGRESSO)
                .nivel(request.getNivel() != null ? 
                    TrilhaAprendizado.NivelTrilha.valueOf(request.getNivel().name()) : 
                    TrilhaAprendizado.NivelTrilha.INTERMEDIARIO)
                .duracaoEstimadaHoras(request.getDuracaoMaximaHoras())
                .build();
            
            trilha = trilhaRepository.save(trilha);
            
            // Atualizar job
            var jobs = jobRepository.findAll().stream()
                .filter(j -> j.getUsuarioId().equals(request.getUsuarioId()) && 
                            j.getTipoJob().equals("GERAR_TRILHA") &&
                            j.getStatus() == JobProcessamento.StatusJob.PENDENTE)
                .findFirst();
            
            if (jobs.isPresent()) {
                JobProcessamento job = jobs.get();
                job.setStatus(JobProcessamento.StatusJob.CONCLUIDO);
                job.setResultado("Trilha criada com ID: " + trilha.getId());
                jobRepository.save(job);
            }
            
            log.info("Trilha {} criada com sucesso para usuário {}", trilha.getId(), usuario.getId());
            
        } catch (Exception e) {
            log.error("Erro ao processar trilha", e);
            var jobs = jobRepository.findAll().stream()
                .filter(j -> j.getUsuarioId().equals(request.getUsuarioId()) && 
                            j.getTipoJob().equals("GERAR_TRILHA") &&
                            j.getStatus() == JobProcessamento.StatusJob.PENDENTE)
                .findFirst();
            
            if (jobs.isPresent()) {
                JobProcessamento job = jobs.get();
                job.setStatus(JobProcessamento.StatusJob.FALHA);
                job.setErro(e.getMessage());
                jobRepository.save(job);
            }
        }
    }
}

