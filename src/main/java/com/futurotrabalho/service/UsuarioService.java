package com.futurotrabalho.service;

import com.futurotrabalho.exception.ResourceNotFoundException;
import com.futurotrabalho.model.dto.UsuarioDTO;
import com.futurotrabalho.model.entity.Usuario;
import com.futurotrabalho.repository.UsuarioRepository;
import com.futurotrabalho.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityMapper mapper;
    
    @Cacheable(value = "usuarios", key = "#id")
    @Transactional(readOnly = true)
    public UsuarioDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        return mapper.toUsuarioDTO(usuario);
    }
    
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAll(Pageable pageable) {
        return usuarioRepository.findByAtivoTrue(pageable)
            .map(mapper::toUsuarioDTO);
    }
    
    @Transactional
    @CacheEvict(value = "usuarios", key = "#id")
    public UsuarioDTO update(Long id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        
        usuario.setNome(dto.getNome());
        usuario.setTelefone(dto.getTelefone());
        
        usuario = usuarioRepository.save(usuario);
        return mapper.toUsuarioDTO(usuario);
    }
}

