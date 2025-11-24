package com.futurotrabalho.mapper;

import com.futurotrabalho.model.dto.*;
import com.futurotrabalho.model.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(config = MapperConfig.class)
public interface EntityMapper {
    
    UsuarioDTO toUsuarioDTO(Usuario usuario);
    Usuario toUsuario(UsuarioDTO dto);
    
    PerfilProfissionalDTO toPerfilProfissionalDTO(PerfilProfissional perfil);
    PerfilProfissional toPerfilProfissional(PerfilProfissionalDTO dto);
    
    SkillDTO toSkillDTO(Skill skill);
    List<SkillDTO> toSkillDTOList(List<Skill> skills);
    Set<SkillDTO> toSkillDTOSet(Set<Skill> skills);
    
    VagaDTO toVagaDTO(Vaga vaga);
    @Mapping(target = "recrutadorId", source = "recrutador.id")
    Vaga toVaga(VagaDTO dto);
    
    TrilhaAprendizadoDTO toTrilhaAprendizadoDTO(TrilhaAprendizado trilha);
    @Mapping(target = "usuarioId", source = "usuario.id")
    TrilhaAprendizado toTrilhaAprendizado(TrilhaAprendizadoDTO dto);
    
    SessaoMentoriaDTO toSessaoMentoriaDTO(SessaoMentoria sessao);
    
    JobResponseDTO toJobResponseDTO(JobProcessamento job);
}

