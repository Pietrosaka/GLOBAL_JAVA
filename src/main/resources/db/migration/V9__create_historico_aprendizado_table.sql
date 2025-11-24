CREATE TABLE historico_aprendizado (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    trilha_id BIGINT,
    skill_id BIGINT,
    acao VARCHAR(100) NOT NULL,
    descricao VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (trilha_id) REFERENCES trilhas_aprendizado(id) ON DELETE SET NULL,
    FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE SET NULL
);

CREATE INDEX idx_historico_usuario ON historico_aprendizado(usuario_id);
CREATE INDEX idx_historico_created_at ON historico_aprendizado(created_at DESC);

