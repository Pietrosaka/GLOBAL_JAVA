package com.futurotrabalho.controller;

import com.futurotrabalho.model.dto.MatchingResponseDTO;
import com.futurotrabalho.model.dto.VagaDTO;
import com.futurotrabalho.service.VagaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vagas")
@RequiredArgsConstructor
@Tag(name = "Vagas", description = "Gerenciamento de vagas e matching")
public class VagaController {
    
    private final VagaService vagaService;
    
    @GetMapping
    @Operation(summary = "Listar vagas", description = "Lista paginada de vagas com filtro opcional por skill")
    public ResponseEntity<Page<VagaDTO>> findAll(
            @RequestParam(required = false) String skill,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        Page<VagaDTO> vagas = vagaService.findAll(skill, pageable);
        return ResponseEntity.ok(vagas);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obter vaga por ID")
    public ResponseEntity<VagaDTO> findById(@PathVariable Long id) {
        VagaDTO vaga = vagaService.findById(id);
        return ResponseEntity.ok(vaga);
    }
    
    @PostMapping
    @Operation(summary = "Criar vaga", description = "Apenas recrutadores podem criar vagas")
    public ResponseEntity<VagaDTO> create(
            @Valid @RequestBody VagaDTO dto,
            Authentication authentication) {
        VagaDTO vaga = vagaService.create(dto, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(vaga);
    }
    
    @GetMapping("/matching/{usuarioId}")
    @Operation(summary = "Buscar vagas compatíveis", description = "Retorna vagas com score de compatibilidade baseado em skills")
    public ResponseEntity<Page<MatchingResponseDTO>> findMatchingVagas(
            @PathVariable Long usuarioId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<MatchingResponseDTO> matching = vagaService.findMatchingVagas(usuarioId, pageable);
        return ResponseEntity.ok(matching);
    }
}

