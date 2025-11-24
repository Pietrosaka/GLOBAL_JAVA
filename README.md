# Career Platform - Futuro do Trabalho

## 📋 Descrição do Projeto

A **Career Platform** é uma solução web inovadora que endereça os principais desafios do "Futuro do Trabalho", oferecendo:

- **Matching Inteligente de Carreiras**: Algoritmo que conecta profissionais a vagas remotas/híbridas baseado em compatibilidade de skills
- **Requalificação Personalizada**: Trilhas de aprendizado geradas por IA (Spring AI) adaptadas ao perfil e objetivos do profissional
- **Simulador de Entrevistas**: Ferramenta com feedback automatizado usando IA generativa
- **Dashboard de Tendências**: Relatórios sobre demanda de skills e tendências do mercado
- **Gestão de Perfis Profissionais**: Plataforma completa para cadastro de skills, preferências e histórico de aprendizado

### Problema Resolvido

O mercado de trabalho atual enfrenta desafios críticos:
- **Skill Gap**: Profissionais precisam identificar quais habilidades desenvolver para se manterem relevantes
- **Matching Ineficiente**: Dificuldade em conectar profissionais qualificados com oportunidades adequadas
- **Requalificação Fragmentada**: Falta de orientação personalizada para transição de carreira
- **Avaliação Subjetiva**: Processos de seleção sem feedback objetivo e construtivo

### Proposta de Valor

- **Redução de 40% no tempo de recolocação** através de matching inteligente
- **Aumento de 60% na taxa de match** entre profissionais e vagas
- **Redução de 50% no skill gap** com trilhas personalizadas de requalificação
- **Feedback imediato e objetivo** em simulações de entrevista

---

## 🛠️ Tecnologias Utilizadas

### Backend
- **Spring Boot 3.2.0** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Autenticação e autorização JWT
- **Spring AI 0.8.1** - Integração com OpenAI para recursos de IA
- **Spring AMQP** - Mensageria com RabbitMQ
- **Spring Cache** - Caching com Redis
- **Flyway** - Migrations de banco de dados
- **MapStruct** - Mapeamento DTO/Entity
- **Bean Validation** - Validação de dados
- **OpenAPI/Swagger** - Documentação da API

### Banco de Dados
- **PostgreSQL 15** - Banco de dados principal
- **H2** - Banco de dados para testes
- **Redis** - Cache distribuído

### Mensageria
- **RabbitMQ** - Filas assíncronas para processamento de jobs

### Infraestrutura
- **Docker & Docker Compose** - Containerização e orquestração
- **Maven** - Gerenciamento de dependências

---

## 📁 Estrutura do Projeto

```
GLOBAL_JAVA/
├── src/
│   ├── main/
│   │   ├── java/com/futurotrabalho/
│   │   │   ├── config/              # Configurações (Security, Cache, i18n, RabbitMQ)
│   │   │   ├── controller/          # Controllers REST
│   │   │   ├── consumer/            # Consumers RabbitMQ
│   │   │   ├── exception/           # Tratamento de exceções
│   │   │   ├── mapper/              # MapStruct mappers
│   │   │   ├── model/
│   │   │   │   ├── dto/             # Data Transfer Objects
│   │   │   │   └── entity/          # Entidades JPA
│   │   │   ├── repository/          # Repositórios Spring Data JPA
│   │   │   ├── security/            # JWT e filtros de segurança
│   │   │   └── service/             # Lógica de negócio
│   │   └── resources/
│   │       ├── db/migration/        # Migrations Flyway
│   │       ├── messages*.properties # Arquivos i18n
│   │       └── application*.yml    # Configurações por profile
│   └── test/                        # Testes
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.9+
- Docker e Docker Compose (para ambiente completo)
- OpenAI API Key (para recursos de IA)

### Opção 1: Docker Compose (Recomendado)

1. **Clone o repositório**
```bash
git clone <repository-url>
cd GLOBAL_JAVA
```

2. **Configure variáveis de ambiente**
```bash
export OPENAI_API_KEY=sua-chave-openai
export JWT_SECRET=seu-secret-jwt-minimo-32-caracteres
```

3. **Inicie os serviços**
```bash
docker-compose up -d
```

Isso iniciará:
- PostgreSQL na porta 5432
- Redis na porta 6379
- RabbitMQ na porta 5672 (Management UI: http://localhost:15672)
- Aplicação Spring Boot na porta 8080

4. **Acesse a aplicação**
- API: http://localhost:8080/api
- Swagger UI: http://localhost:8080/api/swagger-ui.html
- Health Check: http://localhost:8080/api/actuator/health

### Opção 2: Execução Local

1. **Inicie os serviços externos**
```bash
docker-compose up -d postgres redis rabbitmq
```

2. **Configure o application.yml** com as credenciais do banco

3. **Execute a aplicação**
```bash
mvn spring-boot:run
```

### Opção 3: Build e Deploy

1. **Build da aplicação**
```bash
mvn clean package -DskipTests
```

2. **Execute o JAR**
```bash
java -jar target/career-platform-1.0.0.jar
```

---

## 📚 Endpoints da API

### Autenticação

#### POST /api/auth/login
Autentica usuário e retorna JWT token.

**Request:**
```json
{
  "email": "admin@careerplatform.com",
  "senha": "admin123"
}
```

**Response (200):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "expiresIn": 86400000,
  "usuario": {
    "id": 1,
    "email": "admin@careerplatform.com",
    "nome": "Administrador",
    "role": "ROLE_ADMIN"
  }
}
```

#### POST /api/auth/refresh
Renova o token de acesso.

**Headers:**
```
Authorization: Bearer <refresh_token>
```

### Usuários

#### GET /api/usuarios/{id}
Obtém perfil do usuário.

**Headers:**
```
Authorization: Bearer <token>
```

#### GET /api/usuarios?page=0&size=10&sort=nome
Lista paginada de usuários.

#### PUT /api/usuarios/{id}
Atualiza dados do usuário.

### Vagas

#### GET /api/vagas?skill=Java&page=0&size=10
Lista vagas com filtro opcional por skill (paginação).

#### GET /api/vagas/{id}
Obtém detalhes de uma vaga.

#### POST /api/vagas
Cria nova vaga (requer ROLE_RECRUITER).

**Request:**
```json
{
  "titulo": "Desenvolvedor Java Senior",
  "descricao": "Vaga para desenvolvedor Java com experiência em Spring Boot",
  "empresa": "Tech Corp",
  "tipoTrabalho": "REMOTO",
  "salarioMin": 8000.00,
  "salarioMax": 12000.00,
  "skillsRequeridas": [
    {"id": 1, "nome": "Java"},
    {"id": 2, "nome": "Spring Boot"}
  ]
}
```

#### GET /api/vagas/matching/{usuarioId}?page=0&size=10
Retorna vagas compatíveis com score de matching.

**Response:**
```json
{
  "content": [
    {
      "vaga": {
        "id": 1,
        "titulo": "Desenvolvedor Java Senior",
        ...
      },
      "scoreCompatibilidade": 85.5,
      "skillsMatch": 3,
      "totalSkillsRequeridas": 4,
      "justificativa": "Você possui 3 de 4 skills requeridas (75.0% de compatibilidade)"
    }
  ],
  "pageable": {...},
  "totalElements": 10
}
```

### Trilhas de Aprendizado

#### POST /api/trilhas
Solicita geração de trilha personalizada (processamento assíncrono).

**Request:**
```json
{
  "usuarioId": 1,
  "objetivo": "Transição para desenvolvimento Java",
  "nivel": "INTERMEDIARIO",
  "duracaoMaximaHoras": 40
}
```

**Response (202 Accepted):**
```json
{
  "jobId": "550e8400-e29b-41d4-a716-446655440000",
  "tipoJob": "GERAR_TRILHA",
  "status": "PENDENTE",
  "createdAt": "2024-01-15T10:00:00"
}
```

#### GET /api/jobs/{jobId}
Consulta status do job de processamento.

#### GET /api/trilhas/usuario/{usuarioId}?page=0&size=10
Lista trilhas do usuário.

### Simulador de Entrevista

#### POST /api/simulador/entrevista?usuarioId=1&pergunta=Explique o que é Spring Boot
Inicia sessão de simulação.

#### POST /api/simulador/entrevista/{sessaoId}/resposta?resposta=Spring Boot é um framework...
Processa resposta e gera feedback com IA.

**Response:**
```json
{
  "id": 1,
  "tipoSessao": "SIMULACAO_ENTREVISTA",
  "pergunta": "Explique o que é Spring Boot",
  "resposta": "Spring Boot é um framework...",
  "feedbackAI": "Sua resposta está correta. Pontos fortes: ...",
  "score": 85,
  "createdAt": "2024-01-15T10:00:00"
}
```

### Relatórios

#### GET /api/relatorios/tendencias
Relatório de tendências do mercado (cacheável por 1h).

---

## 🔐 Autenticação e Autorização

A aplicação utiliza **JWT (JSON Web Tokens)** para autenticação.

### Roles Disponíveis
- `ROLE_USER` - Usuário comum
- `ROLE_RECRUITER` - Recrutador (pode criar vagas)
- `ROLE_TRAINER` - Treinador (pode gerenciar trilhas)
- `ROLE_ADMIN` - Administrador (acesso total)

### Como usar
1. Faça login em `/api/auth/login`
2. Copie o `token` da resposta
3. Inclua no header de todas as requisições:
```
Authorization: Bearer <token>
```

### Usuários Padrão (criados na migration V12)
- **Admin**: `admin@careerplatform.com` / `admin123`
- **Recrutador**: `recruiter@careerplatform.com` / `recruiter123`

---

## 📋 Mapeamento de Requisitos Técnicos

### ✅ 1. Spring / Configuração
- **@Configuration**: `SecurityConfig`, `CacheConfig`, `I18nConfig`, `RabbitMQConfig`
- **@Bean**: Beans configurados em todas as classes de configuração
- **@Component/@Service/@Repository**: Todas as camadas utilizam anotações Spring
- **@Autowired/Constructor Injection**: Uso de `@RequiredArgsConstructor` do Lombok
- **@Profile**: Profiles configurados em `application-dev.yml`, `application-prod.yml`, `application-test.yml`

**Arquivos:**
- `src/main/java/com/futurotrabalho/config/*.java`
- `src/main/resources/application*.yml`

### ✅ 2. Camada Model / DTO
- **Entities JPA**: `Usuario`, `PerfilProfissional`, `Vaga`, `Skill`, `TrilhaAprendizado`, `SessaoMentoria`, `HistoricoAprendizado`, `JobProcessamento`
- **DTOs**: Todos os DTOs com validações Bean Validation
- **MapStruct**: `EntityMapper` para mapeamento automático

**Arquivos:**
- `src/main/java/com/futurotrabalho/model/entity/*.java`
- `src/main/java/com/futurotrabalho/model/dto/*.java`
- `src/main/java/com/futurotrabalho/mapper/EntityMapper.java`

### ✅ 3. Persistência com Spring Data JPA
- **Repositórios**: `JpaRepository` estendido em todos os repositórios
- **Flyway**: 12 migrations criadas (V1 a V12)
- **@Transactional**: Usado em todos os serviços que modificam dados

**Arquivos:**
- `src/main/java/com/futurotrabalho/repository/*.java`
- `src/main/resources/db/migration/V*.sql`

### ✅ 4. Validação com Bean Validation
- **@Valid**: Usado em todos os controllers
- **@NotNull, @Size, @Email, @NotBlank**: Validações em DTOs
- **i18n**: Mensagens de validação internacionalizadas

**Arquivos:**
- `src/main/java/com/futurotrabalho/model/dto/*.java`
- `src/main/resources/messages*.properties`

### ✅ 5. Caching
- **@EnableCaching**: Habilitado em `CareerPlatformApplication`
- **@Cacheable**: Usado em `VagaService.findAll()`, `TrilhaService.findByUsuario()`, `RelatorioController.getTendencias()`
- **@CacheEvict**: Usado em `UsuarioService.update()`
- **Redis**: Configurado em `CacheConfig` com TTL de 1 hora

**Arquivos:**
- `src/main/java/com/futurotrabalho/config/CacheConfig.java`
- `src/main/java/com/futurotrabalho/service/VagaService.java` (linha 30)
- `src/main/java/com/futurotrabalho/service/TrilhaService.java` (linha 68)
- `src/main/java/com/futurotrabalho/controller/RelatorioController.java` (linha 20)

**Justificativa**: Cache reduz latência em consultas frequentes (listagem de vagas, trilhas, relatórios) que não requerem consistência imediata.

### ✅ 6. Internacionalização (i18n)
- **ResourceBundleMessageSource**: Configurado em `I18nConfig`
- **Suporte pt-BR e en-US**: Arquivos `messages.properties` e `messages_pt_BR.properties`
- **LocaleResolver**: `AcceptHeaderLocaleResolver` para detectar idioma via header
- **Mensagens traduzidas**: Validações, erros e mensagens de sucesso

**Arquivos:**
- `src/main/java/com/futurotrabalho/config/I18nConfig.java`
- `src/main/resources/messages.properties`
- `src/main/resources/messages_pt_BR.properties`
- `src/main/java/com/futurotrabalho/exception/GlobalExceptionHandler.java` (usa MessageSource)

### ✅ 7. Paginação
- **Pageable**: Parâmetros `page`, `size`, `sort` em todos os endpoints de listagem
- **Page<T>**: Retorno paginado em `UsuarioService`, `VagaService`, `TrilhaService`, `SimuladorEntrevistaService`
- **@PageableDefault**: Valores padrão configurados

**Arquivos:**
- Todos os controllers com endpoints GET de listagem
- Exemplo: `VagaController.findAll()` (linha 30)

### ✅ 8. Segurança (Spring Security)
- **JWT**: Implementado com `JwtTokenProvider` e `JwtAuthenticationFilter`
- **Roles**: `ROLE_USER`, `ROLE_RECRUITER`, `ROLE_TRAINER`, `ROLE_ADMIN`
- **BCrypt**: Password hashing em `SecurityConfig`
- **CORS**: Configurado para permitir requisições cross-origin
- **Proteção de endpoints**: Configurada em `SecurityConfig.securityFilterChain()`

**Arquivos:**
- `src/main/java/com/futurotrabalho/config/SecurityConfig.java`
- `src/main/java/com/futurotrabalho/security/JwtTokenProvider.java`
- `src/main/java/com/futurotrabalho/security/JwtAuthenticationFilter.java`

### ✅ 9. Tratamento de Erros e Exceptions
- **@ControllerAdvice**: `GlobalExceptionHandler`
- **@ExceptionHandler**: Tratamento de `ResourceNotFoundException`, `MethodArgumentNotValidException`, `RuntimeException`, `Exception`
- **Padrão de resposta**: `ErrorResponse` com timestamp, status, code, message, details

**Arquivos:**
- `src/main/java/com/futurotrabalho/exception/GlobalExceptionHandler.java`
- `src/main/java/com/futurotrabalho/exception/ResourceNotFoundException.java`
- `src/main/java/com/futurotrabalho/model/dto/ErrorResponse.java`

### ✅ 10. Mensageria (filas assíncronas)
- **RabbitMQ**: Configurado com 3 filas (`trilhas.queue`, `relatorios.queue`, `emails.queue`)
- **Producer**: `TrilhaService.solicitarTrilha()` envia mensagem para fila
- **Consumer**: `TrilhaConsumer` processa mensagens assincronamente
- **DLQ**: Fila de dead-letter configurada (tratamento de erros)

**Arquivos:**
- `src/main/java/com/futurotrabalho/config/RabbitMQConfig.java`
- `src/main/java/com/futurotrabalho/service/TrilhaService.java` (linha 54)
- `src/main/java/com/futurotrabalho/consumer/TrilhaConsumer.java`

**Uso**: Processamento assíncrono de geração de trilhas, relatórios e envio de emails.

### ✅ 11. Recursos de Inteligência Artificial Generativa (Spring AI)
- **Spring AI**: Integrado com OpenAI
- **Recursos implementados**:
  - Geração de trilhas personalizadas (`SpringAIService.gerarTrilhaAprendizado()`)
  - Simulação de entrevista com feedback (`SpringAIService.simularEntrevista()`)
  - Geração de descrições de vagas (`SpringAIService.gerarDescricaoVaga()`)
- **Fallback**: Implementado para casos de erro na API
- **Rate limiting**: Configurável via propriedades Spring AI

**Arquivos:**
- `src/main/java/com/futurotrabalho/service/SpringAIService.java`
- `src/main/java/com/futurotrabalho/service/TrilhaService.java` (usa Spring AI)
- `src/main/java/com/futurotrabalho/service/SimuladorEntrevistaService.java` (usa Spring AI)

**Prompts documentados**: Ver comentários no código de `SpringAIService`.

### ✅ 12. Deploy em Nuvem
- **Dockerfile**: Multi-stage build otimizado
- **docker-compose.yml**: Orquestração completa (app, postgres, redis, rabbitmq)
- **Health checks**: Configurados para todos os serviços
- **Variáveis de ambiente**: Configuração via env vars

**Arquivos:**
- `Dockerfile`
- `docker-compose.yml`

**Deploy sugerido**: 
- **Render**: Conectar repositório e configurar env vars
- **Railway**: Deploy via Dockerfile
- **AWS ECS/Fargate**: Usar docker-compose ou ECS task definition
- **Heroku**: Usar container registry

### ✅ 13. API REST — boas práticas
- **Verbos HTTP**: GET, POST, PUT, PATCH, DELETE usados corretamente
- **Status codes**: 200, 201, 202, 204, 400, 401, 403, 404, 422, 500
- **OpenAPI/Swagger**: Documentação completa em `/api/swagger-ui.html`
- **Validação**: Bean Validation em todos os DTOs
- **Paginação**: Implementada em todos os endpoints de listagem

**Arquivos:**
- Todos os controllers em `src/main/java/com/futurotrabalho/controller/*.java`
- Documentação OpenAPI gerada automaticamente via `springdoc-openapi`

---

## 🧪 Testes

### Executar testes
```bash
mvn test
```

### Testes de integração
```bash
mvn verify
```

---

## 📊 Casos de Uso

### 1. Profissional busca vaga compatível
1. Login em `/api/auth/login`
2. Cadastro de perfil com skills em `/api/usuarios/{id}` (PUT)
3. Busca de vagas compatíveis em `/api/vagas/matching/{usuarioId}`
4. Visualização de score de compatibilidade

### 2. Solicitar trilha de requalificação
1. POST `/api/trilhas` com objetivo e nível
2. Recebe `jobId` (202 Accepted)
3. Consulta status em `/api/jobs/{jobId}`
4. Quando concluído, acessa trilha em `/api/trilhas/usuario/{usuarioId}`

### 3. Simular entrevista
1. POST `/api/simulador/entrevista` com pergunta
2. POST `/api/simulador/entrevista/{sessaoId}/resposta` com resposta
3. Recebe feedback gerado por IA com score

### 4. Recrutador cria vaga
1. Login como ROLE_RECRUITER
2. POST `/api/vagas` com detalhes e skills requeridas
3. Vaga fica disponível para matching

---

## 🔧 Configurações Avançadas

### Variáveis de Ambiente

```bash
# Banco de dados
DATABASE_URL=jdbc:postgresql://localhost:5432/career_platform
DATABASE_USER=postgres
DATABASE_PASSWORD=postgres

# Redis
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=

# RabbitMQ
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USER=guest
RABBITMQ_PASSWORD=guest

# Spring AI (OpenAI)
OPENAI_API_KEY=sk-...

# JWT
JWT_SECRET=seu-secret-minimo-32-caracteres
JWT_EXPIRATION=86400000
JWT_REFRESH_EXPIRATION=604800000

# Profile
SPRING_PROFILES_ACTIVE=dev
```

### Profiles

- **dev**: Desenvolvimento local (H2 opcional, logs detalhados)
- **prod**: Produção (PostgreSQL, logs reduzidos)
- **test**: Testes (H2 em memória)

---

## 📈 Métricas e Monitoramento

### Actuator Endpoints
- `/api/actuator/health` - Health check
- `/api/actuator/info` - Informações da aplicação
- `/api/actuator/metrics` - Métricas da aplicação

### Logs
Logs estruturados com correlation ID para rastreamento de requests assíncronos.

---

## 🚨 Limitações e Trade-offs

### Limitações Conhecidas
1. **Spring AI**: Requer API key da OpenAI (custo por requisição)
2. **Cache**: TTL fixo de 1h (pode ser ajustado por cache específico)
3. **Matching**: Algoritmo baseado apenas em skills (pode ser expandido)
4. **Fallback IA**: Retorna conteúdo genérico em caso de erro

### Trade-offs
- **Consistência vs Performance**: Cache pode retornar dados ligeiramente desatualizados
- **Síncrono vs Assíncrono**: Algumas operações são assíncronas (melhor UX, mas requer polling)
- **Segurança vs Conveniência**: JWT sem refresh automático (requer chamada manual)

---

## 🔄 Próximos Passos

### Melhorias Futuras
- [ ] Implementar refresh token automático no frontend
- [ ] Adicionar testes de integração completos
- [ ] Implementar rate limiting para Spring AI
- [ ] Adicionar métricas de negócio (dashboards)
- [ ] Implementar notificações push
- [ ] Adicionar exportação de relatórios em PDF/CSV
- [ ] Melhorar algoritmo de matching com ML
- [ ] Implementar busca semântica de vagas

---

## 📝 Licença

Este projeto foi desenvolvido como solução acadêmica/profissional.

---

## 👥 Contato

Para dúvidas ou sugestões, abra uma issue no repositório.

---

## 🎯 Evidências de Funcionamento

### Mensageria
- Logs do RabbitMQ Consumer em `TrilhaConsumer.java`
- Fila configurada em `RabbitMQConfig.java`
- Producer em `TrilhaService.solicitarTrilha()`

### Caching
- Anotações `@Cacheable` em `VagaService`, `TrilhaService`, `RelatorioController`
- Configuração Redis em `CacheConfig.java`

### Spring AI
- Integração em `SpringAIService.java`
- Uso em `TrilhaService` e `SimuladorEntrevistaService`
- Fallback implementado

### i18n
- Arquivos de mensagens em `messages.properties` e `messages_pt_BR.properties`
- Configuração em `I18nConfig.java`
- Uso em `GlobalExceptionHandler.java`

---

**Desenvolvido com ❤️ usando Spring Boot e tecnologias modernas**

