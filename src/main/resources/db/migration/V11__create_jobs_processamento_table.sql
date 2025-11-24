CREATE TABLE jobs_processamento (
    id BIGSERIAL PRIMARY KEY,
    job_id VARCHAR(100) NOT NULL UNIQUE,
    tipo_job VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    usuario_id BIGINT,
    dados_entrada TEXT,
    resultado TEXT,
    erro TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE SET NULL
);

CREATE INDEX idx_jobs_job_id ON jobs_processamento(job_id);
CREATE INDEX idx_jobs_status ON jobs_processamento(status);
CREATE INDEX idx_jobs_usuario ON jobs_processamento(usuario_id);

