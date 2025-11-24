CREATE TABLE perfis_profissionais (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    resumo VARCHAR(500),
    experiencia_anos INTEGER,
    data_nascimento DATE,
    cidade VARCHAR(100),
    estado VARCHAR(50),
    pais VARCHAR(50),
    preferencia_trabalho VARCHAR(20),
    pretensao_salarial DECIMAL(10,2),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE INDEX idx_perfis_usuario ON perfis_profissionais(usuario_id);

