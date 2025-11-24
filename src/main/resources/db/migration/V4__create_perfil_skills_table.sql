CREATE TABLE perfil_skills (
    perfil_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    PRIMARY KEY (perfil_id, skill_id),
    FOREIGN KEY (perfil_id) REFERENCES perfis_profissionais(id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE CASCADE
);

CREATE INDEX idx_perfil_skills_skill ON perfil_skills(skill_id);

