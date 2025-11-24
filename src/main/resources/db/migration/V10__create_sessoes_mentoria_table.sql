CREATE TABLE sessoes_mentoria (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    tipo_sessao VARCHAR(50) NOT NULL,
    pergunta VARCHAR(1000),
    resposta TEXT,
    feedback_ai TEXT,
    score INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT chk_score CHECK (score >= 0 AND score <= 100)
);

CREATE INDEX idx_sessoes_usuario ON sessoes_mentoria(usuario_id);
CREATE INDEX idx_sessoes_tipo ON sessoes_mentoria(tipo_sessao);

