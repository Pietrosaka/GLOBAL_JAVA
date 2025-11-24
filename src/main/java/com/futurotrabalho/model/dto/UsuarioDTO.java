package com.futurotrabalho.model.dto;

import com.futurotrabalho.model.entity.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsuarioDTO {
    private Long id;
    
    @NotBlank(message = "{validation.usuario.email.required}")
    @Email(message = "{validation.usuario.email.invalid}")
    private String email;
    
    @NotBlank(message = "{validation.usuario.nome.required}")
    @Size(min = 2, max = 100, message = "{validation.usuario.nome.size}")
    private String nome;
    
    private String telefone;
    private Usuario.Role role;
    private Boolean ativo;
    private LocalDateTime createdAt;
    private PerfilProfissionalDTO perfilProfissional;
}

