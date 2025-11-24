package com.futurotrabalho.controller;

import com.futurotrabalho.model.dto.SessaoMentoriaDTO;
import com.futurotrabalho.service.SimuladorEntrevistaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/simulador")
@RequiredArgsConstructor
@Tag(name = "Simulador de Entrevista", description = "Simulação de entrevistas com feedback de IA")
public class SimuladorController {
    
    private final SimuladorEntrevistaService simuladorService;
    
    @PostMapping("/entrevista")
    @Operation(summary = "Iniciar sessão de entrevista", 
               description = "Inicia uma nova sessão de simulação de entrevista")
    public ResponseEntity<SessaoMentoriaDTO> iniciarSessao(
            @RequestParam Long usuarioId,
            @RequestParam String pergunta) {
        SessaoMentoriaDTO sessao = simuladorService.iniciarSessao(usuarioId, pergunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(sessao);
    }
    
    @PostMapping("/entrevista/{sessaoId}/resposta")
    @Operation(summary = "Processar resposta", 
               description = "Processa a resposta do candidato e gera feedback usando IA")
    public ResponseEntity<SessaoMentoriaDTO> processarResposta(
            @PathVariable Long sessaoId,
            @RequestParam String resposta) {
        SessaoMentoriaDTO sessao = simuladorService.processarResposta(sessaoId, resposta);
        return ResponseEntity.ok(sessao);
    }
    
    @GetMapping("/entrevista/usuario/{usuarioId}")
    @Operation(summary = "Listar sessões do usuário", description = "Lista paginada de sessões de entrevista")
    public ResponseEntity<Page<SessaoMentoriaDTO>> findByUsuario(
            @PathVariable Long usuarioId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        Page<SessaoMentoriaDTO> sessoes = simuladorService.findByUsuario(usuarioId, pageable);
        return ResponseEntity.ok(sessoes);
    }
}

