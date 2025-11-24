package com.futurotrabalho.service;

import com.futurotrabalho.exception.ResourceNotFoundException;
import com.futurotrabalho.model.dto.SessaoMentoriaDTO;
import com.futurotrabalho.model.entity.SessaoMentoria;
import com.futurotrabalho.model.entity.Usuario;
import com.futurotrabalho.repository.SessaoMentoriaRepository;
import com.futurotrabalho.repository.UsuarioRepository;
import com.futurotrabalho.mapper.EntityMapper;
import com.futurotrabalho.service.SpringAIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimuladorEntrevistaService {
    
    private final SessaoMentoriaRepository sessaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SpringAIService springAIService;
    private final EntityMapper mapper;
    
    @Transactional
    public SessaoMentoriaDTO iniciarSessao(Long usuarioId, String pergunta) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario", usuarioId));
        
        SessaoMentoria sessao = SessaoMentoria.builder()
            .usuario(usuario)
            .tipoSessao("SIMULACAO_ENTREVISTA")
            .pergunta(pergunta)
            .build();
        
        sessao = sessaoRepository.save(sessao);
        return mapper.toSessaoMentoriaDTO(sessao);
    }
    
    @Transactional
    public SessaoMentoriaDTO processarResposta(Long sessaoId, String resposta) {
        SessaoMentoria sessao = sessaoRepository.findById(sessaoId)
            .orElseThrow(() -> new ResourceNotFoundException("SessaoMentoria", sessaoId));
        
        sessao.setResposta(resposta);
        
        // Gerar feedback usando Spring AI
        String feedback = springAIService.simularEntrevista(sessao.getPergunta(), resposta);
        sessao.setFeedbackAI(feedback);
        
        // Calcular score simples (pode ser melhorado)
        int score = calcularScore(feedback);
        sessao.setScore(score);
        
        sessao = sessaoRepository.save(sessao);
        return mapper.toSessaoMentoriaDTO(sessao);
    }
    
    @Transactional(readOnly = true)
    public Page<SessaoMentoriaDTO> findByUsuario(Long usuarioId, Pageable pageable) {
        return sessaoRepository.findByUsuarioId(usuarioId, pageable)
            .map(mapper::toSessaoMentoriaDTO);
    }
    
    private int calcularScore(String feedback) {
        // Lógica simples: contar palavras positivas no feedback
        String feedbackLower = feedback.toLowerCase();
        int pontos = 50; // Base
        
        if (feedbackLower.contains("excelente") || feedbackLower.contains("muito bom")) {
            pontos += 30;
        } else if (feedbackLower.contains("bom") || feedbackLower.contains("adequado")) {
            pontos += 15;
        }
        
        if (feedbackLower.contains("melhorar") || feedbackLower.contains("falta")) {
            pontos -= 10;
        }
        
        return Math.max(0, Math.min(100, pontos));
    }
}

