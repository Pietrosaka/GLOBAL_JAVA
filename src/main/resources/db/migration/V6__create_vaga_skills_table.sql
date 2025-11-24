CREATE TABLE vaga_skills (
    vaga_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    PRIMARY KEY (vaga_id, skill_id),
    FOREIGN KEY (vaga_id) REFERENCES vagas(id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE CASCADE
);

CREATE INDEX idx_vaga_skills_skill ON vaga_skills(skill_id);

