package com.futurotrabalho.controller;

import com.futurotrabalho.model.dto.UsuarioDTO;
import com.futurotrabalho.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Gerenciamento de usuários")
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    @GetMapping("/{id}")
    @Operation(summary = "Obter usuário por ID")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }
    
    @GetMapping
    @Operation(summary = "Listar usuários", description = "Lista paginada de usuários")
    public ResponseEntity<Page<UsuarioDTO>> findAll(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<UsuarioDTO> usuarios = usuarioService.findAll(pageable);
        return ResponseEntity.ok(usuarios);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public ResponseEntity<UsuarioDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO dto,
            Authentication authentication) {
        
        // Verificar se o usuário pode atualizar (próprio perfil ou admin)
        String email = authentication.getName();
        UsuarioDTO usuario = usuarioService.update(id, dto);
        return ResponseEntity.ok(usuario);
    }
}

