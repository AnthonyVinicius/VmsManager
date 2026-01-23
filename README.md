🖥️ VMS Manager

===============

**Sistema de Gerenciamento de Máquinas Virtuais**

Aplicação web **full stack** para cadastro, gerenciamento e monitoramento de máquinas virtuais (VMs), desenvolvida como **desafio técnico**, utilizando boas práticas de arquitetura REST, autenticação JWT e separação entre frontend e backend.

📌 Visão Geral

---

O **VMS Manager** permite que usuários autenticados criem, editem e monitorem máquinas virtuais, controlando seus recursos e estados de execução.

Cada VM pertence a um usuário específico, respeitando limites e permissões de acesso.

O sistema também possui um perfil **Administrador**, que pode visualizar e gerenciar todas as VMs cadastradas.

🚀 Funcionalidades

---

### 🔐 Autenticação e Segurança

- Autenticação via **JWT**

- Validação de formato de e-mail

- Token enviado no header:Authorization: Bearer

- Rotas protegidas com **Spring Security**

- Controle de acesso por perfil (**USER** e **ADMIN**)

### 🧑‍💻 Usuários

- Cadastro de usuários

- Cada usuário pode criar **até 5 VMs**

- **Usuário comum**

- Visualiza apenas suas próprias VMs

- **Administrador**

- Visualiza todas as VMs do sistema

### 🖥️ Máquinas Virtuais (VMs)

Cada VM possui:

- ID (gerado automaticamente)

- Nome (mínimo de 5 caracteres)

- CPU (núcleos)

- Memória RAM (GB)

- Disco (GB)

- Status:
  - START

  - STOP

  - SUSPEND

- Data de criação

- Usuário criador (owner)

#### Operações disponíveis:

- Criar VM (respeitando o limite de 5 por usuário)

- Listar VMs

- Visualizar detalhes

- Editar recursos (CPU, memória, disco e nome)

- Alterar status (start, stop, suspend)

- Excluir VM

### 📜 Histórico de Ações

Cada VM mantém um **histórico de eventos**, registrando:

- Criação

- Atualizações

- Mudanças de status

O histórico é exibido na interface.

### 📊 Dashboard

O dashboard exibe:

- Total de VMs

- **Gráfico de barras** (status das VMs)

- **Gráfico de pizza** (uso do limite de 5 VMs)

- Botão **“Nova VM”** desabilitado ao atingir o limite

🛠️ Tecnologias Utilizadas

---

### Backend

- Java 17+

- Spring Boot 4

- Spring Security

- Spring Data JPA

- JWT (JJWT)

- PostgreSQL

- Swagger / OpenAPI (springdoc 3.x)

### Frontend

- Angular 17

- Reactive Forms

- HttpClient

- Interceptor JWT

- Chart.js

- CSS

🗂️ Arquitetura do Projeto

---

### Backend (Spring Boot)

     com.claro.vmsmanager
    ├── configurations
    ├── controller
    ├── dtos
    ├── entities
    ├── exceptions
    ├── repositories
    ├── security
    ├── services
    └── mapper

- Uso de **DTOs** para entrada e saída

- **Services** concentram as regras de negócio

- **Controllers** apenas orquestram as requisições

- JWT tratado via filtro (JwtAuthenticationFilter)

### Frontend (Angular)

    src/app
    ├── pages │
        ├── login │
        ├── register │
        ├── dashboard │
        ├── vm-create │
        ├── vm-edit │
        └── vm-details
    ├── services
    ├── interceptors
    └── app.routes.ts

- Componentes standalone

- Navegação por rotas

- Interceptor automático para envio do JWT

▶️ Como Executar o Projeto

---

### 1️⃣ Backend

#### Pré-requisitos

- Java 17+

- Maven

- PostgreSQL

#### Configuração do banco (application.properties)

    spring.datasource.url=jdbc:postgresql://localhost:5432/vmsmanager

    spring.datasource.username=postgres

     spring.datasource.password=senha

     spring.jpa.hibernate.ddl-auto=update

#### Executar

`mvn clean install mvn spring-boot:run`

Backend disponível em:

`http://localhost:8080`

### 2️⃣ Swagger (Documentação da API)

Após iniciar o backend:

`http://localhost:8080/swagger-ui/index.html`

Permite testar endpoints protegidos utilizando JWT Bearer.

### 3️⃣ Frontend

#### Pré-requisitos

- Node.js 18+

- Angular CLI 17+

#### Instalar dependências

`npm install`

#### Executar

`ng serve`

Frontend disponível em:

`http://localhost:4200`

🔗 Comunicação Frontend ↔ Backend

---

- Formato: **JSON**

- Autenticação: **JWT via Authorization Header**

- CORS configurado para http://localhost:4200

🎯 Objetivo do Projeto

---

Este projeto demonstra conhecimentos em:

- APIs REST com Spring Boot

- Autenticação JWT

- Segurança e controle de acesso

- Modelagem de entidades e regras de negócio

- Frontend moderno com Angular

- Integração frontend/backend

- Visualização de dados com gráficos

- Organização e boas práticas de código

---

👤 Autor

**Anthony Vinicius**
Desenvolvedor Full Stack Java / Angular
