

# Gerenciamento de Usuários e Autenticação

##  Stack de Tecnologias
- **Java 21** | **Spring Boot 3.3.4** (Web, Security, JPA)  
- **Banco de Dados**: PostGrees  
- **Autenticação**: JWT  
- **Ferramentas**: Maven, Java-Dotenv  


## Pré-requisitos

1. **Java JDK 21** ou superio
2. **Maven 3.9+**
3. Um editor de texto ou IDE (ex.: IntelliJ IDEA, VSCode, Eclipse)
4. Banco de dados (PostGrees, é necessário instalá-lo e configurá-lo num servidor ou numa maquina local)


## Configuração do Ambiente

### 1. Obtenha o Código Fonte

Clone este repositório ou faça o download:

```bash
git clone https://github.com/antoniopatricio22/TakeOFF_Squad45-2025.1.git
cd demoApp
```
### 2. Configure as Variáveis de Ambiente
Modifique o arquivo .env na raiz do projeto e defina os valores necessários:

```bash
JWT_SECRET=seu_segredo_aqui #Chave para encriptação
JWT_EXPIRATION=86400000 # Tempo de expiração do token em milissegundos
```

### 3. Configure o Banco de Dados

1 Atualize o arquivo application.properties para usar MySQL como no exemplo:
```bash
spring.datasource.url=jdbc:postgresql://[URL_DA_SUA_DATABASE]/demoapp
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.postgresql.Driver
```


## Como Executar
### 1. Build e Executar Localmente
Compile e inicie o projeto com Maven::
```bash
mvn clean install
mvn spring-boot:run

```
A aplicação estará disponível em http://localhost:8080.


## Deploy
### Opção 1: Servidor Local (Tomcat, Nginx, etc.)
1. Gere o arquivo .jar do projeto:
```bash
mvn clean package

```
2. Suba o .jar em seu servidor:
```bash
java -jar target/demoApp-0.0.1-SNAPSHOT.jar

```

### Opção 2: Render
Render é uma plataforma de nuvem simples para hospedar aplicações. Siga os passos abaixo para realizar o deploy:

1. Preparação do Repositório:
  - Certifique-se de que o repositório do projeto está no GitHub ou GitLab.
  - O arquivo target/demoApp-0.0.1-SNAPSHOT.jar será gerado ao executar o comando mvn clean package.

2. Criação do Serviço no Render:
  - Acesse sua conta no Render.
  - Clique em New + e selecione Web Service.
  - Conecte o repositório do projeto ao Render.

3. Configuração do Serviço:
    - Defina as configurações do serviço:
      - Environment: Configure como Java.
      - Build Command:
        ```bash
        mvn clean install
        ```
      - Start Command:
        ```bash
        java -jar target/demoApp-0.0.1-SNAPSHOT.jar
        ```

4. Configuração de Variáveis de Ambiente:
    - No painel de variáveis de ambiente do Render, configure as variáveis usadas no projeto:
      - JWT_SECRET
      - JWT_EXPIRATION

5. Implantação:
    - Após salvar as configurações, o Render iniciará a implantação.
    - Quando finalizado, será fornecida uma URL pública para acessar a aplicação.
      

---
## Documentação para o Gerenciamento de Usuários
Esse documento descreve os endpoints para Autenticar e Gerencias contas de usuário na API. As responses seguem o 'Standart Response Structure in JSON format" como no exemplo abaixo: 

json
```bash
{
  "status": "success" or "error",
  "message": "Description of the outcome",
  "data": { /* Object with relevant data */ },
  "error": null or { /* Object with error details if any */ }
}
```


## Autenticação
1. **Login**
Endpoint: /auth/login
<br>Method: POST
<br>Description: Autentica um usuário e retorna um JWT token temporário.

Request Body:

json
```bash
{
  "email": "admin@exemplo.com",
  "password": "admin123"
}
```
Response:

- Success:

json
```bash
{
    "status": "success",
    "message": "Login realizado com sucesso",
    "data": {
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBleGVtcGxvLmNvbSIsInJvbGUiOiJST0xFX0FETUlOSVNUUkFET1IiLCJpYXQiOjE3NDM1NDE3MzMsImV4cCI6MTc0MzU1NjEzM30.KzCfRoOjzV_oGjR7z-dL-bobE5bzCT-jOgmxKoj6QuMhqHj9bf_m1j4Bcl_1NA1TqrH1F9FUP7p2cfod5LtAFw",
        "usuario": {
            "id": 1,
            "email": "admin@exemplo.com",
            "nome": "Pedro Paulo",
            "cpf": "016.827.070-67",
            "role": "ADMINISTRADOR"
        }
    },
    "error": null
}
```

- Error:

json
```bash
{
  "status": "error",
  "message": "Falha no login",
  "data": null,
  "error": "Email ou senha inválidos"
}
```

2. **Registro de Proprietário**
Endpoint: /registro/proprietario
<br>Method: POST
<br>Description: Cria uma nova conta de Proprietário.
<br>CarteiraBlockchain é opcional nessa fase


Request Body:

json
```bash
{
  "email": "proprietario@exemplo.com",
  "password": "senha123",
  "confirmPassword": "senha123",
  "nome": "João Silva",
  "cpf": "424.053.410-00",
  "CarteiraBlockchain": "carteira CarteiraBlockchain"
}
```
Response:

- Success:

json
```bash
{
    "status": "success",
    "message": "Proprietário registrado com sucesso",
    "data": {
        "id": 2,
        "email": "proprietario@exemplo.com",
        "role": "PROPRIETARIO",
        "createdAt": "2025-03-29T20:27:05.4686999",
        "updatedAt": "2025-03-29T20:27:05.4686999",
        "lastLogin": null,
        "nome": "Nome do Proprietário",
        "cpf": null,
        "carteiraBlockchain": null
    },
    "error": null
}
```

- Error: Usuário com email já cadastrado

json
```bash
{
    "status": "error",
    "message": "Erro de validação",
    "data": null,
    "error": "Email já cadastrado"
}
```

- Error: Senhas não coincidem

json
```bash
{
    "status": "error",
    "message": "Erro de validação",
    "data": null,
    "error": "As senhas não coincidem"
}
```
- Error: CPF já Cadastrado.

json
```bash
{
    "status": "error",
    "message": "Erro de validação",
    "data": null,
    "error": "CPF já cadastrado"
}
```

- Error: CPF inválido, mal formatado ou ausente.

Error: 403 Forbidden

Resposta resposta de erro customizada a ser implementada.

3. **Registro de Administrador**
Endpoint: /registro/administrador
<br>Method: POST
<br>Description: Cria uma nova conta de Administrador.


Request Headers:

Authorization: Bearer <jwt_token_here>

Request Body:

json
```bash
{
    "email": "paulo@exemplo.com",
    "password": "senha123",
    "confirmPassword": "senha123",
    "nome": "Nome do Proprietário",
    "cpf": "747.183.590-74"
}
```

Response:

- Success:

json
```bash
{
    "status": "success",
    "message": "Administrador registrado com sucesso",
    "data": {
        "id": 3,
        "email": "paulo@exemplo.com",
        "nome": "Nome do Proprietário",
        "cpf": "747.183.590-74",
        "role": "ADMINISTRADOR"
    },
    "error": null
}
```

Demais Erros Equivalentes aos de Registro de Proprietário

---

Vou ajustar os exemplos de requests e responses para cada rota do `PropriedadeController` conforme suas solicitações, mantendo a estrutura atual do sistema e as modificações pedidas (dados reduzidos do proprietário e remoção do status nas listagens).

4. **Cadastrar Propriedade** 
Endpoint: /propriedades
<br>Method: POST
<br>Description: Cadastra uma novo Propriedade.

Request Headers:

Headers:
  Authorization: <jwt_token_here>



Request Body:

json
```bash

Body:
{
  "nome": "Fazenda Verde",
  "logradouro": "Rodovia BR-101",
  "numero": "Km 125",
  "cidade": "São Paulo",
  "estado": "SP",
  "pais": "Brasil",
  "areaPreservada": 1500.5,
  "producaoCarbono": 250.75
}
```
$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
**Response (Success - 201 Created):**
```json
{
  "success": true,
  "message": "Propriedade cadastrada com sucesso",
  "data": {
    "id": 1,
    "nome": "Fazenda Verde",
    "logradouro": "Rodovia BR-101",
    "numero": "Km 125",
    "cidade": "São Paulo",
    "estado": "SP",
    "pais": "Brasil",
    "areaPreservada": 1500.5,
    "producaoCarbono": 250.75,
    "status": "PENDENTE",
    "mensagemStatus": "Aguardando revisão",
    "dataCadastro": "2023-11-20T14:30:45.123456",
    "proprietario": {
      "id": 5,
      "carteiraBlockchain": "0x71C7656EC7ab88b098defB751B7401B5f6d8976F"
    }
  }
}
```

### 2. Listar Propriedades (GET /propriedades)

**a) Para Administrador:**
```json
GET /propriedades
Headers:
  Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response (Success - 200 OK):**
```json
{
  "success": true,
  "message": "Propriedades listadas com sucesso",
  "data": [
    {
      "id": 1,
      "nome": "Fazenda Verde",
      "logradouro": "Rodovia BR-101",
      "numero": "Km 125",
      "cidade": "São Paulo",
      "estado": "SP",
      "pais": "Brasil",
      "areaPreservada": 1500.5,
      "producaoCarbono": 250.75,
      "status": "PENDENTE",
      "mensagemStatus": "Aguardando revisão",
      "dataCadastro": "2023-11-20T14:30:45.123456",
      "proprietario": {
        "id": 5,
        "carteiraBlockchain": "0x71C7656EC7ab88b098defB751B7401B5f6d8976F"
      }
    },
    {
      "id": 2,
      "nome": "Sítio Florestal",
      "logradouro": "Estrada Rural",
      "numero": "S/N",
      "cidade": "Curitiba",
      "estado": "PR",
      "pais": "Brasil",
      "areaPreservada": 800.0,
      "producaoCarbono": 150.25,
      "status": "APROVADO",
      "mensagemStatus": "Área preservada dentro dos padrões",
      "dataCadastro": "2023-11-18T09:15:22.987654",
      "proprietario": {
        "id": 8,
        "carteiraBlockchain": "0xAb5801a7D398351b8bE11C439e05C5B3259aeC9B"
      }
    }
  ]
}
```

**b) Para Proprietário:**
```json
GET /propriedades
Headers:
  Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response:**
```json
{
  "success": true,
  "message": "Propriedades listadas com sucesso",
  "data": [
    {
      "id": 1,
      "nome": "Fazenda Verde",
      "logradouro": "Rodovia BR-101",
      "numero": "Km 125",
      "cidade": "São Paulo",
      "estado": "SP",
      "pais": "Brasil",
      "areaPreservada": 1500.5,
      "producaoCarbono": 250.75,
      "dataCadastro": "2023-11-20T14:30:45.123456"
    }
  ]
}
```

### 3. Validar Propriedade (PUT /propriedades/{id}/validar)

**Request (Aprovação com mensagem customizada):**
```json
PUT /propriedades/1/validar?status=APROVADO&mensagem=Área%20preservada%20dentro%20dos%20padrões
Headers:
  Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response (Success - 200 OK):**
```json
{
  "success": true,
  "message": "Propriedade validada com sucesso",
  "data": {
    "id": 1,
    "nome": "Fazenda Verde",
    "status": "APROVADO",
    "mensagemStatus": "Área preservada dentro dos padrões",
    "proprietario": {
      "id": 5,
      "carteiraBlockchain": "0x71C7656EC7ab88b098defB751B7401B5f6d8976F"
    }
  }
}
```

**Request (Recusa com motivo):**
```json
PUT /propriedades/1/validar?status=RECUSADO&mensagem=Área%20preservada%20insuficiente
Headers:
  Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response:**
```json
{
  "success": true,
  "message": "Propriedade validada com sucesso",
  "data": {
    "id": 1,
    "nome": "Fazenda Verde",
    "status": "RECUSADO",
    "mensagemStatus": "Área preservada insuficiente",
    "proprietario": {
      "id": 5,
      "carteiraBlockchain": "0x71C7656EC7ab88b098defB751B7401B5f6d8976F"
    }
  }
}
```

### Respostas de Erro:

**403 Forbidden - Usuário não é proprietário (ao cadastrar):**
```json
{
  "success": false,
  "message": "Acesso negado",
  "error": "Apenas proprietários podem cadastrar propriedades"
}
```

**403 Forbidden - Usuário não é administrador (ao validar):**
```json
{
  "success": false,
  "message": "Acesso negado",
  "error": "Apenas administradores podem validar propriedades"
}
```

**404 Not Found - Propriedade não encontrada:**
```json
{
  "success": false,
  "message": "Erro ao validar propriedade",
  "error": "Propriedade não encontrada"
}
```

