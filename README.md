# Sistema de Inscricao para Eventos

API REST para gerenciar eventos, participantes e inscricoes, com controle de vagas, lista de espera e cancelamento com promocao automatica.

## Recursos principais

- Eventos: CRUD completo e controle de vagas disponiveis.
- Participantes: cadastro com validacao de email e cpf.
- Inscricoes: confirmacao automatica, lista de espera e cancelamento com promocao.

## Tecnologias

- Java 21
- Spring Boot 4
- Spring Data JPA
- MySQL
- Lombok
- Springdoc OpenAPI (Swagger)

## Requisitos

- Java 21
- MySQL 8+

## Configuracao

Variaveis de ambiente necessarias:

- `DATABASE_USERNAME`
- `DATABASE_PASSWORD`

Banco configurado em `src/main/resources/application.yaml`:

```
jdbc:mysql://localhost:3306/sistema-evento?createDatabaseIfNotExist=true&serverTimezone=UTC
```

## Como executar

```
./gradlew bootRun
```

Aplicacao sobe em `http://localhost:8080`.

## Documentacao da API

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Endpoints

Base path: `v1`

### Eventos

- `POST /v1/eventos`
- `GET /v1/eventos`
- `GET /v1/eventos/{id}`
- `PUT /v1/eventos/{id}`
- `DELETE /v1/eventos/{id}`

Observacao: se `vagasDisponiveis` nao for enviado no create, o valor recebe `capacidadeMaxima` automaticamente.

### Participantes

- `POST /v1/participantes`
- `GET /v1/participantes`
- `GET /v1/participantes/{id}`
- `PUT /v1/participantes/{id}`
- `DELETE /v1/participantes/{id}`

### Inscricoes

- `POST /v1/inscricoes`
- `GET /v1/inscricoes`
- `GET /v1/inscricoes/{id}`
- `PATCH /v1/inscricoes/{id}/cancelar`
- `GET /v1/inscricoes/eventos/{id}/participantes`

Status possiveis: `CONFIRMADA`, `LISTA_DE_ESPERA`, `CANCELADA`.

## Exemplos

Criar evento:

```bash
curl -X POST http://localhost:8080/v1/eventos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Conexao Tech",
    "dataHorario": "2026-10-15T19:00:00",
    "localizacao": "Auditorio Principal",
    "capacidadeMaxima": 150
  }'
```

Criar participante:

```bash
curl -X POST http://localhost:8080/v1/participantes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Gabriel Leite",
    "email": "gabriel@email.com",
    "cpf": "12345678900"
  }'
```

Realizar inscricao:

```bash
curl -X POST http://localhost:8080/v1/inscricoes \
  -H "Content-Type: application/json" \
  -d '{
    "participanteId": "UUID_DO_PARTICIPANTE",
    "eventoId": "UUID_DO_EVENTO"
  }'
```

Cancelar inscricao:

```bash
curl -X PATCH http://localhost:8080/v1/inscricoes/UUID_DA_INSCRICAO/cancelar
```

## Testes

```
./gradlew test
```
