# 🎮 NEXTAGE Gamer Store

A **NEXTAGE Gamer Store** é uma aplicação de e-commerce voltada para computadores, hardware e produtos gamer.

O projeto possui **frontend, backend e banco de dados**, executados em containers Docker.

## 🏗️ Arquitetura do projeto

A aplicação utiliza a seguinte arquitetura:

```text
Frontend
React + Vite
Porta 5173
     │
     ▼
Backend
Spring Boot
Porta 8080
     │
     ▼
Banco de Dados
PostgreSQL
Porta 5432
```

## 🚀 Tecnologias utilizadas

### Frontend
- React
- Vite
- JavaScript
- HTML
- CSS
- React Router
- Axios

### Backend
- Java 17
- Spring Boot
- Maven
- API REST
- OpenAPI / Swagger

### Banco de Dados
- PostgreSQL

### Infraestrutura
- Docker
- Docker Compose

## 📂 Estrutura do projeto

```text
NEXTAGE-Docker-ENTREGA/
├── backend/
│   ├── src/
│   ├── Dockerfile
│   ├── pom.xml
│   └── mvnw
│
├── frontend/
│   ├── src/
│   ├── public/
│   ├── Dockerfile
│   └── package.json
│
├── backup-lojapc.sql
├── docker-compose.yml
└── README.md
```

## 🛒 Funcionalidades

A aplicação possui funcionalidades como:

- Página inicial da loja
- Exibição de produtos
- Categorias de produtos
- Cadastro de usuários
- Login de usuários
- Login administrativo
- Gerenciamento de produtos
- Cadastro, edição e exclusão de produtos
- Carrinho de compras
- Montagem personalizada de PC
- Finalização de pedidos
- Tela de pagamento
- Histórico de pedidos

## 🐳 Como executar com Docker

### Requisitos

É necessário possuir o **Docker Desktop** instalado e em execução.

### Primeira execução

Abra um terminal na pasta raiz do projeto e execute:

```bash
docker compose up -d --build
```

Esse comando cria e inicia os containers do:

- PostgreSQL
- Spring Boot
- React

O banco de dados é inicializado utilizando o arquivo:

```text
backup-lojapc.sql
```

### Acessar a aplicação

Após os containers iniciarem, acesse:

```text
http://localhost:5173
```

### Backend

A API Spring Boot fica disponível em:

```text
http://localhost:8080
```

### Banco de dados

O PostgreSQL utiliza a porta:

```text
5432
```

## ⏹️ Parar os containers

Para parar e remover os containers criados pelo Docker Compose:

```bash
docker compose down
```

## 🔄 Executar novamente

Caso os containers já tenham sido criados anteriormente, eles também podem ser iniciados utilizando o Docker Desktop ou os comandos Docker correspondentes.

## 📦 Containers

A aplicação utiliza três serviços principais:

```text
frontend-loja  → React
backend-loja   → Spring Boot
pdbbackm       → PostgreSQL
```

## 🎓 Projeto acadêmico

Projeto desenvolvido para fins acadêmicos, utilizando uma arquitetura com **frontend, backend e banco de dados integrados através do Docker**.

## 👥 Integrantes

- Gabriel de Lima Ayres Tinoco
- Brenda Cardozo Pereira
- Gustavo Santos de Almeida
- Matheus Paiva David
- Guilherme Ferreira Alves Biserra