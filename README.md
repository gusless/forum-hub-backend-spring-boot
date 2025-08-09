# 📚 FórumHub API - Spring Boot

[![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green?logo=springboot)](https://spring.io/projects/spring-boot)
[![JWT](https://img.shields.io/badge/JWT-Security-orange?logo=jsonwebtokens)](https://jwt.io)
[![Maven](https://img.shields.io/badge/Maven-Build-red?logo=apachemaven)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/license-MIT-lightgrey)](LICENSE)

---

## 📖 Sobre o Projeto

API REST para um **fórum de cursos**, onde usuários podem registrar, criar tópicos, responder, atualizar e deletar conteúdo, com autenticação via **JWT** e controle de permissões via **Spring Security**.

---

## 🚀 Tecnologias Utilizadas

- Java 21+
- Spring Boot (Web, Data JPA, Security, Validation)
- JWT (JSON Web Token)
- Spring Data JPA / Hibernate
- Banco de dados: H2 / MySQL / PostgreSQL (configurável)
- Maven

---

## 📂 Estrutura (resumo)

```src/main/java
src/main/java/com/seuprojeto/
│
├── controller/
│   ├── AuthenticationController.java
│   ├── CursoController.java
│   ├── RespostaController.java
│   ├── RespostaLivreController.java
│   └── TopicoController.java
│
├── domain/
│   ├── curso/
│   │   ├── Curso.java
│   │   ├── CursoRepository.java
│   │   └── dto/
│   │       └── (DTOs de entrada/saída)
│   │
│   ├── resposta/
│   │   ├── Resposta.java
│   │   ├── RespostaRepository.java
│   │   └── dto/
│   │       └── (DTOs de entrada/saída)
│   │
│   ├── topico/
│   │   ├── Topico.java
│   │   ├── TopicoRepository.java
│   │   └── dto/
│   │       └── (DTOs de entrada/saída)
│   │
│   └── usuario/
│       ├── Usuario.java
│       ├── UsuarioRepository.java
│       └── dto/
│           └── (DTOs de entrada/saída)
│
├── security/
│   ├── SecurityConfigurations.java
│   ├── SecurityFilter.java
│   ├── SecurityUtils.java
│   ├── TokenService.java
│   └── AuthorizationService.java
│
└── Application.java (main)
```

---

## 🔐 Autenticação

- A API usa JWT. O fluxo é:
    1. `/auth/register` — cria usuário
    2. `/auth/login` — autentica e recebe token JWT
    3. Inclua no header:
       Authorization: Bearer SEU_TOKEN_AQUI

---

## 📌 Endpoints e Modelos de Requisição

### Autenticação
| Método | Endpoint           | Descrição |
|--------|--------------------|-----------|
| POST   | /auth/register     | Registra novo usuário |
| POST   | /auth/login        | Faz login e retorna token |

**Exemplo registro:**

`POST /auth/register`

```json
{
"nome": "João da Silva",
"email": "joao@email.com",
"senha": "123456"
}
```

**Exemplo login:**

`POST /auth/login`

```json
{
"login": "joao@email.com",
"password": "123456"
}
```

Resposta de login:

```json
{
"token": "eyJhbGciOiJ..."
}
```

### Cursos
| Método | Endpoint           | Permissão |
|--------|--------------------|-----------|
| GET    | /cursos            | Público   |
| GET    | /cursos/{id}       | Público   |
| POST   | /cursos            | ADMIN     |
| PUT    | /cursos/{id}       | ADMIN     |
| DELETE | /cursos/{id}       | ADMIN     |

**Criar Curso**

`POST /cursos`

```json
{
"nome": "string",
"categoria": "string"  // Exemplo: "JAVA", "SPRING_BOOT", etc. ->CONFERIR O ENUM CATEGORIA<-
}
```

**Atualizar Curso**

`PUT /cursos/{id}`

```json
{
"nome": "string",
"categoria": "string"
}
```

### Tópicos
| Método | Endpoint              | Permissão |
|--------|-----------------------|-----------|
| GET    | /topicos              | Público   |
| GET    | /topicos/{id}         | Público   |
| POST   | /topicos              | Usuário autenticado |
| PUT    | /topicos/{id}         | Autor do tópico |
| DELETE | /topicos/{id}         | Autor ou ADMIN |

**Criar Tópico**

`POST /topicos`

```json
{
"titulo": "string",
"mensagem": "string",
"idCurso": 1
}
```

**Atualizar Tópico**

`PUT /topicos/{id}`

```json
{
"titulo": "string",
"mensagem": "string",
"idCurso": 1,
"status": "string"  // "RESOLVIDO", "ABERTO" ou "FECHADO".
}
```

### Respostas
| Método | Endpoint                                              | Permissão |
|--------|-------------------------------------------------------|-----------|
| GET    | /topicos/{id}/respostas                               | Público   |
| POST   | /topicos/{id}/respostas                               | Usuário autenticado |
| PUT    | /topicos/{id}/respostas/{idResposta}                  | Autor     |
| DELETE | /topicos/{id}/respostas/{idResposta}                  | Autor ou ADMIN |
| GET    | /respostas                                            | Público   |
| GET    | /respostas/{id}                                       | Público   |

**Criar Resposta**

`POST /topicos/{id}/respostas`

```json
{
"mensagem": "string"
}
```

**Atualizar Resposta**

`PUT /topicos/{id}/respostas/{idResposta}`

```json
{
"mensagem": "string"
}
```

---

## 🛡️ Controle de acesso
- Usuário comum:
    - Criar tópicos e respostas
    - Editar/excluir apenas seus próprios conteúdos
- Administrador (ROLE_ADMIN):
    - Criar/editar/excluir cursos
    - Excluir qualquer tópico ou resposta

---

## ⚙️ Configuração e execução

1. Clone o repositório:
   git clone https://github.com/seu-usuario/forum-api.git
   cd forum-api

2. Configure o banco no `src/main/resources/application.properties` (exemplo MySQL):
   
``` properties
spring.datasource.url=jdbc:mysql://localhost/forum
spring.datasource.username=root
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
api.security.token.secret=SUA_CHAVE_SECRETA
```

3. Execute:
   mvn spring-boot:run

4. A API ficará disponível em:
   http://localhost:8080