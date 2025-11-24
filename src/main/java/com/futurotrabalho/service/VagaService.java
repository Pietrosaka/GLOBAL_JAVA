package com.futurotrabalho.service;

import com.futurotrabalho.exception.ResourceNotFoundException;
import com.futurotrabalho.model.dto.MatchingResponseDTO;
import com.futurotrabalho.model.dto.VagaDTO;
import com.futurotrabalho.model.entity.Skill;
import com.futurotrabalho.model.entity.Usuario;
import com.futurotrabalho.model.entity.Vaga;
import com.futurotrabalho.repository.PerfilProfissionalRepository;
import com.futurotrabalho.repository.SkillRepository;
import com.futurotrabalho.repository.UsuarioRepository;
import com.futurotrabalho.repository.VagaRepository;
import com.futurotrabalho.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VagaService {
    
    private final VagaRepository vagaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SkillRepository skillRepository;
    private final PerfilProfissionalRepository perfilRepository;
    private final EntityMapper mapper;
    
    @Cacheable(value = "vagas", key = "#pageable.pageNumber + '_' + #pageable.pageSize + '_' + (#skillName != null ? #skillName : 'all')")
    @Transactional(readOnly = true)
    public Page<VagaDTO> findAll(String skillName, Pageable pageable) {
        if (skillName != null && !skillName.isEmpty()) {
            return vagaRepository.findBySkillName(skillName, pageable)
                .map(mapper::toVagaDTO);
        }
        return vagaRepository.findByAtivaTrue(pageable)
            .map(mapper::toVagaDTO);
    }
    
    @Transactional(readOnly = true)
    public VagaDTO findById(Long id) {
        Vaga vaga = vagaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Vaga", id));
        return mapper.toVagaDTO(vaga);
    }
    
    @Transactional
    public VagaDTO create(VagaDTO dto, String emailRecrutador) {
        Usuario recrutador = usuarioRepository.findByEmail(emailRecrutador)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario", emailRecrutador));
        
        Vaga vaga = mapper.toVaga(dto);
        vaga.setRecrutador(recrutador);
        
        if (dto.getSkillsRequeridas() != null && !dto.getSkillsRequeridas().isEmpty()) {
            Set<Long> skillIds = dto.getSkillsRequeridas().stream()
                .map(skill -> skill.getId())
                .collect(Collectors.toSet());
            Set<Skill> skills = skillRepository.findByIds(skillIds);
            vaga.setSkillsRequeridas(skills);
        }
        
        vaga = vagaRepository.save(vaga);
        return mapper.toVagaDTO(vaga);
    }
    
    @Transactional(readOnly = true)
    public Page<MatchingResponseDTO> findMatchingVagas(Long usuarioId, Pageable pageable) {
        var perfil = perfilRepository.findByUsuarioId(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("PerfilProfissional", usuarioId));
        
        Set<Long> userSkillIds = perfil.getSkills().stream()
            .map(Skill::getId)
            .collect(Collectors.toSet());
        
        Page<Vaga> vagas = vagaRepository.findBySkillsRequeridas(userSkillIds, pageable);
        
        return vagas.map(vaga -> {
            Set<Long> vagaSkillIds = vaga.getSkillsRequeridas().stream()
                .map(Skill::getId)
                .collect(Collectors.toSet());
            
            long matchingSkills = userSkillIds.stream()
                .filter(vagaSkillIds::contains)
                .count();
            
            double score = vaga.getSkillsRequeridas().isEmpty() ? 0.0 :
                (matchingSkills * 100.0) / vaga.getSkillsRequeridas().size();
            
            String justificativa = String.format(
                "Você possui %d de %d skills requeridas (%.1f%% de compatibilidade)",
                matchingSkills, vaga.getSkillsRequeridas().size(), score
            );
            
            return MatchingResponseDTO.builder()
                .vaga(mapper.toVagaDTO(vaga))
                .scoreCompatibilidade(score)
                .skillsMatch((int) matchingSkills)
                .totalSkillsRequeridas(vaga.getSkillsRequeridas().size())
                .justificativa(justificativa)
                .build();
        });
    }
}

