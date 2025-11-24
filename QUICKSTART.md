# Quick Start Guide

## Início Rápido (5 minutos)

### 1. Pré-requisitos
- Docker e Docker Compose instalados
- OpenAI API Key (opcional, mas necessário para recursos de IA)

### 2. Configurar variáveis de ambiente
```bash
export OPENAI_API_KEY=sua-chave-aqui
export JWT_SECRET=seu-secret-jwt-minimo-32-caracteres-para-producao
```

### 3. Iniciar aplicação
```bash
docker-compose up -d
```

### 4. Aguardar inicialização
Aguarde aproximadamente 30 segundos para todos os serviços iniciarem.

### 5. Verificar saúde
```bash
curl http://localhost:8080/api/actuator/health
```

### 6. Acessar Swagger UI
Abra no navegador: http://localhost:8080/api/swagger-ui.html

### 7. Fazer login
Use as credenciais padrão:
- Email: `admin@careerplatform.com`
- Senha: `admin123`

### 8. Testar endpoints
Veja exemplos em `EXAMPLES.md`

## Parar aplicação
```bash
docker-compose down
```

## Parar e remover volumes (limpar dados)
```bash
docker-compose down -v
```

## Ver logs
```bash
docker-compose logs -f app
```

## Acessar serviços

- **Aplicação**: http://localhost:8080/api
- **Swagger**: http://localhost:8080/api/swagger-ui.html
- **RabbitMQ Management**: http://localhost:15672 (guest/guest)
- **PostgreSQL**: localhost:5432 (postgres/postgres)
- **Redis**: localhost:6379

