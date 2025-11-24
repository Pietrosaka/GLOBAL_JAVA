package com.futurotrabalho.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    
    @NotBlank(message = "{validation.usuario.email.required}")
    @Email(message = "{validation.usuario.email.invalid}")
    private String email;
    
    @NotBlank(message = "{validation.senha.required}")
    private String senha;
}

