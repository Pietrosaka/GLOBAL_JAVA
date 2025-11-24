CREATE TABLE trilhas_aprendizado (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descricao VARCHAR(2000) NOT NULL,
    conteudo_gerado_ai TEXT,
    usuario_id BIGINT,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    duracao_estimada_horas INTEGER,
    nivel VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE SET NULL
);

CREATE INDEX idx_trilhas_usuario ON trilhas_aprendizado(usuario_id);
CREATE INDEX idx_trilhas_status ON trilhas_aprendizado(status);

