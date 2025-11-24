CREATE TABLE trilha_skills (
    trilha_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    PRIMARY KEY (trilha_id, skill_id),
    FOREIGN KEY (trilha_id) REFERENCES trilhas_aprendizado(id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE CASCADE
);

