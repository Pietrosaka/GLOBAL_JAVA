package com.futurotrabalho.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "perfis_profissionais")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerfilProfissional {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;
    
    @Column(length = 500)
    private String resumo;
    
    @Column(name = "experiencia_anos")
    private Integer experienciaAnos;
    
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    
    @Column(length = 100)
    private String cidade;
    
    @Column(length = 50)
    private String estado;
    
    @Column(length = 50)
    private String pais;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "preferencia_trabalho")
    private PreferenciaTrabalho preferenciaTrabalho;
    
    @Column(name = "pretensao_salarial")
    private Double pretensaoSalarial;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "perfil_skills",
        joinColumns = @JoinColumn(name = "perfil_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();
    
    public enum PreferenciaTrabalho {
        REMOTO,
        HIBRIDO,
        PRESENCIAL,
        FLEXIVEL
    }
}

