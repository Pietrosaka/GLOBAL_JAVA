package com.futurotrabalho.controller;

import com.futurotrabalho.model.dto.JobResponseDTO;
import com.futurotrabalho.model.dto.TrilhaAprendizadoDTO;
import com.futurotrabalho.model.dto.TrilhaRequestDTO;
import com.futurotrabalho.service.TrilhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trilhas")
@RequiredArgsConstructor
@Tag(name = "Trilhas de Aprendizado", description = "Gerenciamento de trilhas de requalificação")
public class TrilhaController {
    
    private final TrilhaService trilhaService;
    
    @PostMapping
    @Operation(summary = "Solicitar trilha personalizada", 
               description = "Cria um job assíncrono para gerar trilha usando IA. Retorna jobId para acompanhamento.")
    public ResponseEntity<JobResponseDTO> solicitarTrilha(@Valid @RequestBody TrilhaRequestDTO request) {
        JobResponseDTO job = trilhaService.solicitarTrilha(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(job);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar trilhas do usuário", description = "Lista paginada de trilhas do usuário")
    public ResponseEntity<Page<TrilhaAprendizadoDTO>> findByUsuario(
            @PathVariable Long usuarioId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        Page<TrilhaAprendizadoDTO> trilhas = trilhaService.findByUsuario(usuarioId, pageable);
        return ResponseEntity.ok(trilhas);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obter trilha por ID")
    public ResponseEntity<TrilhaAprendizadoDTO> findById(@PathVariable Long id) {
        TrilhaAprendizadoDTO trilha = trilhaService.findById(id);
        return ResponseEntity.ok(trilha);
    }
}

