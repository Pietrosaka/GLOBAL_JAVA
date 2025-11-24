-- Inserir skills iniciais
INSERT INTO skills (nome, descricao, categoria, demanda_mercado) VALUES
('Java', 'Linguagem de programação Java', 'LINGUAGEM', 85),
('Spring Boot', 'Framework Spring Boot para desenvolvimento Java', 'FERRAMENTA', 90),
('Python', 'Linguagem de programação Python', 'LINGUAGEM', 88),
('JavaScript', 'Linguagem de programação JavaScript', 'LINGUAGEM', 92),
('React', 'Biblioteca JavaScript para interfaces', 'FERRAMENTA', 87),
('Docker', 'Plataforma de containerização', 'FERRAMENTA', 82),
('Kubernetes', 'Orquestração de containers', 'FERRAMENTA', 75),
('AWS', 'Amazon Web Services', 'FERRAMENTA', 80),
('Agile', 'Metodologia ágil de desenvolvimento', 'METODOLOGIA', 70),
('Comunicação', 'Habilidade de comunicação interpessoal', 'COMPORTAMENTAL', 85),
('Liderança', 'Habilidade de liderança de equipes', 'COMPORTAMENTAL', 78),
('Resolução de Problemas', 'Capacidade de resolver problemas complexos', 'COMPORTAMENTAL', 88);

-- Criar usuário admin padrão (senha: admin123)
INSERT INTO usuarios (email, senha, nome, role, ativo) VALUES
('admin@careerplatform.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8pJ5C', 'Administrador', 'ROLE_ADMIN', true);

-- Criar usuário recrutador padrão (senha: recruiter123)
INSERT INTO usuarios (email, senha, nome, role, ativo) VALUES
('recruiter@careerplatform.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iwK8pJ5C', 'Recrutador', 'ROLE_RECRUITER', true);

