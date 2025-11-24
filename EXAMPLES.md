# Exemplos de Uso da API

## Autenticação

### 1. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@careerplatform.com",
    "senha": "admin123"
  }'
```

### 2. Usar Token
```bash
export TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

curl -X GET http://localhost:8080/api/usuarios/1 \
  -H "Authorization: Bearer $TOKEN"
```

## Vagas

### Listar vagas com filtro
```bash
curl -X GET "http://localhost:8080/api/vagas?skill=Java&page=0&size=10" \
  -H "Authorization: Bearer $TOKEN"
```

### Criar vaga (Recrutador)
```bash
curl -X POST http://localhost:8080/api/vagas \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Desenvolvedor Java Senior",
    "descricao": "Vaga para desenvolvedor Java com experiência em Spring Boot e microserviços",
    "empresa": "Tech Corp",
    "tipoTrabalho": "REMOTO",
    "salarioMin": 8000.00,
    "salarioMax": 12000.00,
    "skillsRequeridas": [
      {"id": 1},
      {"id": 2}
    ]
  }'
```

### Buscar matching de vagas
```bash
curl -X GET "http://localhost:8080/api/vagas/matching/1?page=0&size=10" \
  -H "Authorization: Bearer $TOKEN"
```

## Trilhas de Aprendizado

### Solicitar trilha personalizada
```bash
curl -X POST http://localhost:8080/api/trilhas \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "usuarioId": 1,
    "objetivo": "Transição para desenvolvimento Java",
    "nivel": "INTERMEDIARIO",
    "duracaoMaximaHoras": 40
  }'
```

### Consultar status do job
```bash
curl -X GET "http://localhost:8080/api/jobs/550e8400-e29b-41d4-a716-446655440000" \
  -H "Authorization: Bearer $TOKEN"
```

### Listar trilhas do usuário
```bash
curl -X GET "http://localhost:8080/api/trilhas/usuario/1?page=0&size=10" \
  -H "Authorization: Bearer $TOKEN"
```

## Simulador de Entrevista

### Iniciar sessão
```bash
curl -X POST "http://localhost:8080/api/simulador/entrevista?usuarioId=1&pergunta=Explique%20o%20que%20é%20Spring%20Boot" \
  -H "Authorization: Bearer $TOKEN"
```

### Enviar resposta
```bash
curl -X POST "http://localhost:8080/api/simulador/entrevista/1/resposta?resposta=Spring%20Boot%20é%20um%20framework%20que%20facilita%20o%20desenvolvimento%20de%20aplicações%20Java" \
  -H "Authorization: Bearer $TOKEN"
```

## Relatórios

### Obter tendências (cacheável)
```bash
curl -X GET http://localhost:8080/api/relatorios/tendencias \
  -H "Authorization: Bearer $TOKEN"
```

## Internacionalização

### Requisição em Português
```bash
curl -X GET http://localhost:8080/api/usuarios/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Accept-Language: pt-BR"
```

### Requisição em Inglês
```bash
curl -X GET http://localhost:8080/api/usuarios/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Accept-Language: en-US"
```

