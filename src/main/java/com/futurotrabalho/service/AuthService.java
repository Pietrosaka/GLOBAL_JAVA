package com.futurotrabalho.service;

import com.futurotrabalho.model.dto.LoginRequest;
import com.futurotrabalho.model.dto.LoginResponse;
import com.futurotrabalho.model.dto.UsuarioDTO;
import com.futurotrabalho.model.entity.Usuario;
import com.futurotrabalho.repository.UsuarioRepository;
import com.futurotrabalho.security.JwtTokenProvider;
import com.futurotrabalho.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final EntityMapper mapper;
    
    @Transactional
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("error.authentication.failed"));
        
        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("error.authentication.failed");
        }
        
        if (!usuario.getAtivo()) {
            throw new RuntimeException("error.authentication.failed");
        }
        
        String token = tokenProvider.generateToken(usuario.getEmail(), usuario.getRole().name());
        String refreshToken = tokenProvider.generateRefreshToken(usuario.getEmail(), usuario.getRole().name());
        
        UsuarioDTO usuarioDTO = mapper.toUsuarioDTO(usuario);
        
        return LoginResponse.builder()
            .token(token)
            .refreshToken(refreshToken)
            .tipo("Bearer")
            .expiresIn(86400000L) // 24 horas
            .usuario(usuarioDTO)
            .build();
    }
    
    public LoginResponse refreshToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("error.authentication.failed");
        }
        
        String email = tokenProvider.getEmailFromToken(refreshToken);
        String role = tokenProvider.getRoleFromToken(refreshToken);
        
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("error.authentication.failed"));
        
        String newToken = tokenProvider.generateToken(email, role);
        String newRefreshToken = tokenProvider.generateRefreshToken(email, role);
        
        UsuarioDTO usuarioDTO = mapper.toUsuarioDTO(usuario);
        
        return LoginResponse.builder()
            .token(newToken)
            .refreshToken(newRefreshToken)
            .tipo("Bearer")
            .expiresIn(86400000L)
            .usuario(usuarioDTO)
            .build();
    }
}

