CREATE TABLE vagas (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descricao VARCHAR(2000) NOT NULL,
    recrutador_id BIGINT NOT NULL,
    empresa VARCHAR(100),
    tipo_trabalho VARCHAR(20) NOT NULL,
    salario_min DECIMAL(10,2),
    salario_max DECIMAL(10,2),
    localizacao VARCHAR(100),
    ativa BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (recrutador_id) REFERENCES usuarios(id)
);

CREATE INDEX idx_vagas_recrutador ON vagas(recrutador_id);
CREATE INDEX idx_vagas_ativa ON vagas(ativa);
CREATE INDEX idx_vagas_tipo_trabalho ON vagas(tipo_trabalho);

