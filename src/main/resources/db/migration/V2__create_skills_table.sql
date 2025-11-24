CREATE TABLE skills (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao VARCHAR(500),
    categoria VARCHAR(50) NOT NULL,
    demanda_mercado INTEGER,
    CONSTRAINT chk_demanda CHECK (demanda_mercado >= 0 AND demanda_mercado <= 100)
);

CREATE INDEX idx_skills_nome ON skills(nome);
CREATE INDEX idx_skills_categoria ON skills(categoria);
CREATE INDEX idx_skills_demanda ON skills(demanda_mercado DESC);

