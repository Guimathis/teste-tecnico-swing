# Teste Técnico — Java Desktop (Swing + PostgreSQL)

Aplicação desktop desenvolvida em **Java 21** com **Java Swing** e **PostgreSQL** (JDBC nativo), seguindo a arquitetura **MVC + DAO**.

## Tecnologias Utilizadas

- **Java 21**
- **Java Swing** (compatível com NetBeans 14 GUI Builder)
- **PostgreSQL 16** (Driver JDBC `org.postgresql:postgresql`)
- **Apache Maven**
- **JUnit 5** (Testes automatizados)
- **Docker & Docker Compose**

## Pré-requisitos

- **JDK 21** instalado e configurado no `PATH`
- **Apache Maven** (ou utilize o Maven embutido no NetBeans 14)
- **PostgreSQL** em execução (via **Docker** ou instalação local)

## Configuração do Banco de Dados

A aplicação se conecta por padrão em `jdbc:postgresql://localhost:5432/saam_db` (usuário `postgres` e senha `postgres`), criando automaticamente as tabelas necessárias (`usuarios` e `funcionarios`) na inicialização através do script `src/main/resources/sql/schema.sql`.

### Opção 1: Subir o banco via Docker (Recomendado)

Na pasta `testeTecnicoSwing`, execute:

```bash
docker compose up -d
```

### Opção 2: Usar PostgreSQL Local

Caso utilize uma instância local do PostgreSQL, certifique-se de que o banco `saam_db` exista:

```sql
CREATE DATABASE saam_db;
```

Se desejar customizar as credenciais ou porta, defina as variáveis de ambiente antes de iniciar a aplicação:
- `DB_URL` (padrão: `jdbc:postgresql://localhost:5432/saam_db`)
- `DB_USER` (padrão: `postgres`)
- `DB_PASSWORD` (padrão: `postgres`)

## Executando os Testes

```bash
mvn clean test
```

## Executando a Aplicação

Pelo terminal (Maven):

```bash
mvn compile exec:java
```

Ou abra o projeto diretamente no **NetBeans 14** (`File > Open Project > testeTecnicoSwing`) e pressione **F6 (Run Project)**.

