# 🏋️ API Academia

Um sistema REST API profissional desenvolvido em **Java 21 com Spring Boot** para gerenciar academias, incluindo gestão de alunos, professores e administradores.

**Status:** 🚧 Em desenvolvimento (MVP)

---

## 📋 Sumário

- [Características](#características)
- [Stack Tecnológico](#stack-tecnológico)
- [Arquitetura](#arquitetura)
- [Instalação](#instalação)
- [Variáveis de Ambiente](#variáveis-de-ambiente)
- [Como Usar](#como-usar)
- [Endpoints](#endpoints)
- [Exemplos de Requisições](#exemplos-de-requisições)
- [Tratamento de Erros](#tratamento-de-erros)
- [Autenticação](#autenticação)
- [Boas Práticas](#boas-práticas)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Contribuindo](#contribuindo)
- [Licença](#licença)

---

## ✨ Características

### ✅ Implementado

- ✅ **Gestão de Usuários** - Alunos, Professores e Administradores
- ✅ **Sistema de Roles** - ROLE_ADMIN, ROLE_PROFESSOR, ROLE_ALUNO
- ✅ **Validação de Dados** - Email, CPF e CREF únicos
- ✅ **Criptografia de Senha** - BCrypt
- ✅ **Tratamento de Exceções** - Centralizado e consistente
- ✅ **Logs Estruturados** - SLF4J com Logback
- ✅ **Documentação Swagger** - Integrada e automática
- ✅ **DTOs Separados** - Entrada e saída de dados
- ✅ **Transações ACID** - Integridade de dados
- ✅ **Arquitetura em Camadas** - Clean Architecture

### 🔄 Planejado

- 🔄 Autenticação JWT
- 🔄 Autorização granular por role
- 🔄 Matrícula de alunos
- 🔄 Atribuição de turmas
- 🔄 Horários e aulas
- 🔄 Pagamentos e cobranças

---

## 🛠️ Stack Tecnológico

| Componente | Versão | Descrição |
|-----------|--------|-----------|
| **Java** | 21 | Linguagem de programação |
| **Spring Boot** | 3.x | Framework web |
| **Spring Data JPA** | 3.x | Persistência de dados |
| **Spring Security** | 3.x | Segurança (JWT futuro) |
| **PostgreSQL** | 14+ | Banco de dados |
| **Maven** | 3.8+ | Gerenciador de dependências |
| **Lombok** | 1.18+ | Redução de boilerplate |
| **Springdoc OpenAPI** | 2.x | Documentação Swagger/OpenAPI |
| **JUnit 5** | 5.x | Testes unitários |

---

## 🏗️ Arquitetura

### Padrão: **Clean Architecture em Camadas**

```
┌──────────────────────────────────────────────┐
│           Presentation Layer                  │
│  (Controllers & DTOs)                        │
└──────────────────┬───────────────────────────┘
                   │
┌──────────────────▼───────────────────────────┐
│           Application Layer                   │
│  (Services & Business Logic)                 │
└──────────────────┬───────────────────────────┘
                   │
┌──────────────────▼───────────────────────────┐
│           Data Layer                          │
│  (Repositories & JPARepository)              │
└──────────────────┬───────────────────────────┘
                   │
┌──────────────────▼───────────────────────────┐
│           Database Layer                      │
│  (PostgreSQL)                                │
└───────────────────────────────────────────────┘
```

### Estrutura de Pastas

```
src/
├── main/
│   ├── java/com/academia/apiacademia/
│   │   ├── config/              # Configurações (Spring Security, Swagger, BD)
│   │   ├── controller/          # Endpoints REST
│   │   ├── service/             # Lógica de negócio
│   │   ├── repository/          # Acesso a dados
│   │   ├── entity/              # Entidades JPA
│   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── input/           # DTOs de entrada
│   │   │   └── output/          # DTOs de saída
│   │   ├── exception/           # Tratamento de exceções
│   │   ├── util/                # Utilitários
│   │   └── ApiacademiaApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/academia/apiacademia/
```

### Diagrama de Fluxo

```
Cliente HTTP
    │
    ▼
┌─────────────────────┐
│   Controller        │  ← Recebe requisição
└────────┬────────────┘
         │ Valida com @Valid
         ▼
┌─────────────────────┐
│   Service           │  ← Lógica de negócio
│   - Validações      │
│   - Conversões      │
└────────┬────────────┘
         │ Acessa dados
         ▼
┌─────────────────────┐
│   Repository        │  ← Query builder
└────────┬────────────┘
         │ SQL
         ▼
┌─────────────────────┐
│   PostgreSQL        │  ← Persiste/Recupera
└─────────────────────┘
```

---

## 📦 Instalação

### Pré-requisitos

- **Java 21** ou superior
- **PostgreSQL 14** ou superior
- **Maven 3.8** ou superior
- **Git**

### Passo 1: Clonar o repositório

```bash
git clone https://github.com/seu-usuario/api-academia.git
cd api-academia
```

### Passo 2: Configurar banco de dados

```bash
# Conectar ao PostgreSQL
psql -U postgres

# Criar banco de dados
CREATE DATABASE api_academia;

# Criar usuário (opcional)
CREATE USER api_user WITH PASSWORD 'sua_senha';
GRANT ALL PRIVILEGES ON DATABASE api_academia TO api_user;
```

### Passo 3: Configurar variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/api_academia
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=sua_senha

# JPA/Hibernate
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false

# Application
SERVER_PORT=8080
SPRING_APPLICATION_NAME=api-academia
```

### Passo 4: Instalar dependências

```bash
mvn clean install
```

### Passo 5: Executar a aplicação

```bash
mvn spring-boot:run
```

Ou compilar e executar o JAR:

```bash
mvn clean package
java -jar target/apiacademia-1.0.0.jar
```

**A API estará disponível em:** `http://localhost:8080`

**Documentação Swagger:** `http://localhost:8080/swagger-ui.html`

---

## 🔐 Variáveis de Ambiente

### Obrigatórias

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/api_academia
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=senha123
```

### Opcionais

```env
# Porta da aplicação (padrão: 8080)
SERVER_PORT=8080

# Log level (DEBUG, INFO, WARN, ERROR)
LOGGING_LEVEL_ROOT=INFO

# JPA/Hibernate
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false

# Swagger
SPRINGDOC_SWAGGER_UI_ENABLED=true
SPRINGDOC_API_DOCS_ENABLED=true
```

---

## 🚀 Como Usar

### 1. Criar um Aluno

```bash
curl -X POST http://localhost:8080/v1/alunos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@email.com",
    "senha": "senha123",
    "cpf": "12345678901",
    "telefone": "11999999999"
  }'
```

### 2. Buscar Aluno por CPF

```bash
curl -X GET http://localhost:8080/v1/alunos/cpf/12345678901
```

### 3. Buscar Alunos por Nome

```bash
curl -X GET "http://localhost:8080/v1/alunos/buscar?nome=João"
```

### 4. Atualizar Aluno

```bash
curl -X PUT http://localhost:8080/v1/alunos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva Atualizado",
    "telefone": "11988888888"
  }'
```

### 5. Deletar Aluno

```bash
curl -X DELETE http://localhost:8080/v1/alunos/1
```

---

## 📚 Endpoints

### Base URL: `/v1`

### 👨‍🎓 Alunos

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| POST | `/alunos` | Criar aluno | 201 Created |
| GET | `/alunos/buscar?nome=x` | Buscar por nome | 200 OK |
| GET | `/alunos/cpf/{cpf}` | Buscar por CPF | 200 OK |
| PUT | `/alunos/{id}` | Atualizar aluno | 200 OK |
| DELETE | `/alunos/{id}` | Deletar aluno | 204 No Content |

### 👨‍🏫 Professores

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| POST | `/professores` | Criar professor | 201 Created |
| GET | `/professores/buscar?nome=x` | Buscar por nome | 200 OK |
| GET | `/professores/cref/{cref}` | Buscar por CREF | 200 OK |
| GET | `/professores/cpf/{cpf}` | Buscar por CPF | 200 OK |
| PUT | `/professores/{id}` | Atualizar professor | 200 OK |
| DELETE | `/professores/{id}` | Deletar professor | 204 No Content |

### 🔑 Admins

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| POST | `/admins` | Criar admin | 201 Created |
| GET | `/admins/buscar?nome=x` | Buscar por nome | 200 OK |
| PUT | `/admins/{id}` | Atualizar admin | 200 OK |
| DELETE | `/admins/{id}` | Deletar admin | 204 No Content |

---

## 🔄 Exemplos de Requisições

### Criar Aluno

```http
POST /v1/alunos HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "nome": "Maria Santos",
  "email": "maria@email.com",
  "senha": "senhaSegura123",
  "cpf": "98765432100",
  "telefone": "11987654321"
}
```

**Resposta (201 Created):**

```json
{
  "id": 1,
  "nome": "Maria Santos",
  "email": "maria@email.com",
  "cpf": "98765432100",
  "telefone": "11987654321",
  "ativo": true,
  "role": "ROLE_ALUNO",
  "criadoEm": "2026-03-01T10:30:00",
  "atualizadoEm": "2026-03-01T10:30:00"
}
```

### Criar Professor

```http
POST /v1/professores HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "nome": "Carlos Oliveira",
  "email": "carlos@email.com",
  "senha": "senhaSegura123",
  "cpf": "11111111111",
  "cref": "1234567",
  "telefone": "11912345678"
}
```

**Resposta (201 Created):**

```json
{
  "id": 2,
  "nome": "Carlos Oliveira",
  "email": "carlos@email.com",
  "cpf": "11111111111",
  "cref": "1234567",
  "telefone": "11912345678",
  "ativo": true,
  "role": "ROLE_PROFESSOR",
  "criadoEm": "2026-03-01T10:35:00",
  "atualizadoEm": "2026-03-01T10:35:00"
}
```

### Buscar por Nome

```http
GET /v1/alunos/buscar?nome=Maria HTTP/1.1
Host: localhost:8080
```

**Resposta (200 OK):**

```json
[
  {
    "id": 1,
    "nome": "Maria Santos",
    "email": "maria@email.com",
    "cpf": "98765432100",
    "telefone": "11987654321",
    "ativo": true,
    "role": "ROLE_ALUNO",
    "criadoEm": "2026-03-01T10:30:00",
    "atualizadoEm": "2026-03-01T10:30:00"
  }
]
```

### Atualizar Aluno

```http
PUT /v1/alunos/1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "telefone": "11999999999",
  "nome": "Maria Silva Santos"
}
```

**Resposta (200 OK):**

```json
{
  "id": 1,
  "nome": "Maria Silva Santos",
  "email": "maria@email.com",
  "cpf": "98765432100",
  "telefone": "11999999999",
  "ativo": true,
  "role": "ROLE_ALUNO",
  "criadoEm": "2026-03-01T10:30:00",
  "atualizadoEm": "2026-03-01T10:45:00"
}
```

### Deletar Aluno

```http
DELETE /v1/alunos/1 HTTP/1.1
Host: localhost:8080
```

**Resposta (204 No Content):** (Sem corpo)

---

## ⚠️ Tratamento de Erros

Todos os erros retornam um JSON estruturado:

### Email Duplicado

```http
POST /v1/alunos HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "email_existente@email.com",
  "senha": "senha123",
  "cpf": "12345678901",
  "telefone": "11999999999"
}
```

**Resposta (400 Bad Request):**

```json
{
  "timestamp": "2026-03-01T10:50:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Recurso 'Usuário' com valor 'email_existente@email.com' para campo 'email' já existe",
  "path": "/v1/alunos"
}
```

### CPF Duplicado

```json
{
  "timestamp": "2026-03-01T10:50:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Recurso 'Aluno' com valor '12345678901' para campo 'cpf' já existe",
  "path": "/v1/alunos"
}
```

### Não Encontrado

```http
GET /v1/alunos/cpf/00000000000 HTTP/1.1
Host: localhost:8080
```

**Resposta (404 Not Found):**

```json
{
  "timestamp": "2026-03-01T10:50:00",
  "status": 404,
  "error": "Not Found",
  "message": "Recurso 'Aluno' com valor '00000000000' para campo 'cpf' não foi encontrado",
  "path": "/v1/alunos/cpf/00000000000"
}
```

### Validação Falhou

```http
POST /v1/alunos HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "nome": "",
  "email": "invalido",
  "senha": "123",
  "cpf": "123",
  "telefone": "123"
}
```

**Resposta (400 Bad Request):**

```json
{
  "timestamp": "2026-03-01T10:50:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Erro na validação dos dados",
  "errors": [
    "Nome é obrigatório",
    "Email deve ser válido",
    "Senha deve ter no mínimo 6 caracteres",
    "CPF deve ter 11 dígitos",
    "Telefone é obrigatório"
  ]
}
```

### Códigos de Status HTTP

| Código | Descrição | Quando Ocorre |
|--------|-----------|---------------|
| **200** | OK | Requisição bem-sucedida (GET, PUT) |
| **201** | Created | Recurso criado com sucesso (POST) |
| **204** | No Content | Recurso deletado (DELETE) |
| **400** | Bad Request | Dados inválidos ou recurso duplicado |
| **404** | Not Found | Recurso não encontrado |
| **409** | Conflict | Conflito (ex: email duplicado) |
| **500** | Internal Server Error | Erro no servidor |

---

## 🔐 Autenticação

**Status:** 🔄 Em desenvolvimento

Autenticação JWT será implementada nos próximos sprints.

Quando implementada:

```bash
# Login
curl -X POST http://localhost:8080/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "usuario@email.com", "senha": "senha123"}'

# Resposta
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "expiraEm": 3600
}

# Usar token em requisições
curl -X GET http://localhost:8080/v1/alunos/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

## ✅ Boas Práticas

### Implementadas

✅ **DTOs Separados** - Nunca expõe entidades diretamente
```java
// ❌ Errado
@GetMapping
public List<User> listar() { return users; }

// ✅ Correto
@GetMapping
public List<AlunoResponse> listar() { return dtos; }
```

✅ **Transações ACID** - Garantia de integridade
```java
@Transactional
public AlunoResponse registrarAluno(CreateAlunoRequest request) {
    // Tudo ou nada
}
```

✅ **Validação em Múltiplas Camadas**
```java
// Controller - @Valid
@PostMapping
public ResponseEntity<AlunoResponse> criar(
    @Valid @RequestBody CreateAlunoRequest request
)

// Service - Validações customizadas
if (userRepository.existsByEmail(request.getEmail())) {
    throw new DuplicateResourceException(...)
}
```

✅ **Tratamento Centralizado de Exceções**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(...) { }
}
```

✅ **Logs Estruturados**
```java
log.info("Registrando novo aluno com email: {}", request.getEmail());
log.warn("Email já cadastrado: {}", request.getEmail());
log.error("Erro ao processar aluno", e);
```

✅ **REST Semanticamente Correto**
```
GET    /v1/alunos          - Listar todos
POST   /v1/alunos          - Criar novo
GET    /v1/alunos/{id}     - Obter um
PUT    /v1/alunos/{id}     - Atualizar
DELETE /v1/alunos/{id}     - Deletar
```

### Recomendações

- ✅ Sempre use DTOs
- ✅ Prefira composição à herança
- ✅ Use Optional ao invés de null
- ✅ Valide nos Controllers
- ✅ Adicione logs nas operações críticas
- ✅ Use transações onde apropriado
- ✅ Documente seus endpoints no Swagger
- ✅ Escreva testes para casos críticos

---

## 📁 Estrutura do Projeto

```
api-academia/
├── src/
│   ├── main/
│   │   ├── java/com/academia/apiacademia/
│   │   │   ├── ApiacademiaApplication.java
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   ├── SwaggerConfig.java
│   │   │   │   └── DataInitializationConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AlunoController.java
│   │   │   │   ├── ProfessorController.java
│   │   │   │   └── AdminController.java
│   │   │   ├── service/
│   │   │   │   ├── AlunoService.java
│   │   │   │   ├── ProfessorService.java
│   │   │   │   └── AdminService.java
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java
│   │   │   ├── entity/
│   │   │   │   ├── User.java
│   │   │   │   └── Role.java
│   │   │   ├── dto/
│   │   │   │   ├── input/
│   │   │   │   │   ├── CreateAlunoRequest.java
│   │   │   │   │   ├── CreateProfessorRequest.java
│   │   │   │   │   ├── CreateAdminRequest.java
│   │   │   │   │   └── Update*.java
│   │   │   │   └── output/
│   │   │   │       ├── AlunoResponse.java
│   │   │   │       ├── ProfessorResponse.java
│   │   │   │       └── AdminResponse.java
│   │   │   ├── exception/
│   │   │   │   ├── BusinessException.java
│   │   │   │   ├── DuplicateResourceException.java
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   └── handler/
│   │   │   │       ├── ErrorResponse.java
│   │   │   │       └── GlobalExceptionHandler.java
│   │   │   └── util/
│   │   │       └── UserMapper.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/academia/apiacademia/
├── .env
├── .gitignore
├── pom.xml
├── README.md
└── mvnw / mvnw.cmd
```

---

## 🧪 Testes

### Executar Testes

```bash
# Todos os testes
mvn test

# Teste específico
mvn test -Dtest=AlunoServiceTest

# Com cobertura
mvn test jacoco:report
```

### Estrutura de Testes

```java
@SpringBootTest
class AlunoServiceTest {
    
    @Test
    void deveCriarAlunoComSucesso() {
        // Given
        CreateAlunoRequest request = new CreateAlunoRequest(...);
        
        // When
        AlunoResponse response = alunoService.registrarAluno(request);
        
        // Then
        assertNotNull(response.getId());
        assertEquals(request.getNome(), response.getNome());
    }
    
    @Test
    void nãoDeveCriarAlunoComEmailDuplicado() {
        // Given
        CreateAlunoRequest request = new CreateAlunoRequest(...);
        alunoService.registrarAluno(request);
        
        // When & Then
        assertThrows(DuplicateResourceException.class, () -> {
            alunoService.registrarAluno(request);
        });
    }
}
```

---

## 🤝 Contribuindo

1. Faça um Fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -am 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

### Padrões de Commit

```
feat: adiciona nova funcionalidade
fix: corrige bug
docs: adiciona documentação
test: adiciona testes
refactor: refatora código
style: muda estilo (formatação, etc)
```

---

## 📜 Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

---

## 📞 Suporte

Se encontrar problemas:

1. Verifique a documentação do [Swagger](http://localhost:8080/swagger-ui.html)
2. Consulte a seção [Tratamento de Erros](#tratamento-de-erros)
3. Abra uma [Issue](https://github.com/seu-usuario/api-academia/issues)

---

## 🗺️ Roadmap

- [x] Entidades base (User, Role)
- [x] Controllers REST (Aluno, Professor, Admin)
- [x] Services com lógica de negócio
- [x] DTOs de entrada/saída
- [x] Tratamento de exceções
- [x] Documentação Swagger
- [ ] Autenticação JWT
- [ ] Autorização por role
- [ ] Testes unitários
- [ ] Testes de integração
- [ ] Deploy em produção
- [ ] Matrícula de alunos
- [ ] Gestão de turmas
- [ ] Pagamentos

---

## 👨‍💻 Desenvolvedor

**Seu Nome**  
- GitHub: [@seu-usuario](https://github.com/seu-usuario)
- Email: seu.email@email.com

---

## 📚 Referências

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [REST API Best Practices](https://restfulapi.net/)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

---

**Última atualização:** 01/03/2026

