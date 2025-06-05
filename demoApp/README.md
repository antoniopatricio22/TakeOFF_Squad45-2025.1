# Gerenciamento de Usuários e Autenticação

## Stack de Tecnologias
- **Java 21** | **Spring Boot 3.3.4** (Web, Security, JPA)  
- **Banco de Dados**: PostgreSQL  
- **Autenticação**: JWT  
- **Ferramentas**: Maven, Java-Dotenv  

## Pré-requisitos

1. **Java JDK 21** ou superior
2. **Maven 3.9+**
3. Um editor de texto ou IDE (ex.: IntelliJ IDEA, VSCode, Eclipse)
4. Banco de dados (PostgreSQL, é necessário instalá-lo e configurá-lo em um servidor ou máquina local)

## Configuração do Ambiente

### 1. Obtenha o Código Fonte

Clone este repositório ou faça o download:

```bash
git clone https://github.com/antoniopatricio22/TakeOFF_Squad45-2025.1.git
cd demoApp
```

### 2. Configure as Variáveis de Ambiente

Modifique o arquivo `.env` na raiz do projeto e defina os valores necessários:

```bash
JWT_SECRET=seu_segredo_aqui # Chave para encriptação
JWT_EXPIRATION=86400000 # Tempo de expiração do token em milissegundos
```

### 3. Configure o Banco de Dados

Atualize o arquivo `application.properties` para usar PostgreSQL como no exemplo:

```properties
spring.datasource.url=jdbc:postgresql://[URL_DA_SUA_DATABASE]/demoapp
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
```

## Como Executar

### 1. Build e Executar Localmente

Compile e inicie o projeto com Maven:

```bash
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em [http://localhost:8080](http://localhost:8080).

## Deploy

### Opção 1: Servidor Local (Tomcat, Nginx, etc.)

1. Gere o arquivo `.jar` do projeto:

```bash
mvn clean package
```

2. Suba o `.jar` em seu servidor:

```bash
java -jar target/demoApp-0.0.1-SNAPSHOT.jar
```

### Opção 2: Render

Render é uma plataforma de nuvem simples para hospedar aplicações. Siga os passos abaixo para realizar o deploy:

1. **Preparação do Repositório**:
   - Certifique-se de que o repositório do projeto está no GitHub ou GitLab.
   - O arquivo `target/demoApp-0.0.1-SNAPSHOT.jar` será gerado ao executar o comando `mvn clean package`.

2. **Criação do Serviço no Render**:
   - Acesse sua conta no Render.
   - Clique em **New +** e selecione **Web Service**.
   - Conecte o repositório do projeto ao Render.

3. **Configuração do Serviço**:
   - Defina as configurações do serviço:
     - **Environment**: Configure como Java.
     - **Build Command**:
       ```bash
       mvn clean install
       ```
     - **Start Command**:
       ```bash
       java -jar target/demoApp-0.0.1-SNAPSHOT.jar
       ```

4. **Configuração de Variáveis de Ambiente**:
   - No painel de variáveis de ambiente do Render, configure as variáveis usadas no projeto:
     - `JWT_SECRET`
     - `JWT_EXPIRATION`

5. **Implantação**:
   - Após salvar as configurações, o Render iniciará a implantação.
   - Quando finalizado, será fornecida uma URL pública para acessar a aplicação.

---

## Documentação para o Gerenciamento de Usuários

Esse documento descreve os endpoints para autenticar e gerenciar contas de usuário na API. As respostas seguem a estrutura padrão em JSON, como no exemplo abaixo:

```json
{
  "status": "success" or "error",
  "message": "Descrição do resultado",
  "data": { /* Objeto com dados relevantes */ },
  "error": null ou { /* Detalhes do erro, se houver */ }
}
```

## Autenticação

### 1. Login

**Endpoint**: `/auth/login`  
**Method**: `POST`  
**Descrição**: Autentica um usuário e retorna um token JWT temporário.

**Request Body**:

```json
{
  "email": "admin@exemplo.com",
  "password": "admin123"
}
```

**Response**:

- **Success**:

```json
{
  "status": "success",
  "message": "Login realizado com sucesso",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9...",
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

- **Error**:

```json
{
  "status": "error",
  "message": "Falha no login",
  "data": null,
  "error": "Email ou senha inválidos"
}
```

### 2. Registro de Proprietário

**Endpoint**: `/registro/proprietario`  
**Method**: `POST`  
**Descrição**: Cria uma nova conta de proprietário. O campo `CarteiraBlockchain` é opcional.

**Request Body**:

```json
{
  "email": "proprietario@exemplo.com",
  "password": "senha123",
  "confirmPassword": "senha123",
  "nome": "João Silva",
  "cpf": "424.053.410-00",
  "carteiraBlockchain": "0x71C7656EC7ab88b098defB751B7401B5f6d8976F"
}
```

**Response**:

- **Success**:

```json
{
  "status": "success",
  "message": "Proprietário registrado com sucesso",
  "data": {
    "id": 2,
    "email": "proprietario@exemplo.com",
    "role": "PROPRIETARIO",
    "nome": "João Silva",
    "cpf": "424.053.410-00",
    "carteiraBlockchain": "0x71C7656EC7ab88b098defB751B7401B5f6d8976F"
  },
  "error": null
}
```

- **Error**: Usuário com email já cadastrado

```json
{
  "status": "error",
  "message": "Erro de validação",
  "data": null,
  "error": "Email já cadastrado"
}
```

- **Error**: Senhas não coincidem

```json
{
  "status": "error",
  "message": "Erro de validação",
  "data": null,
  "error": "As senhas não coincidem"
}
```

- **Error**: CPF já cadastrado

```json
{
  "status": "error",
  "message": "Erro de validação",
  "data": null,
  "error": "CPF já cadastrado"
}
```

- **Error**: CPF inválido, mal formatado ou ausente.

Error: 403 Forbidden

Resposta resposta de erro customizada a ser implementada.

### 3. Registro de Administrador

**Endpoint**: `/registro/administrador`  
**Method**: `POST`  
**Descrição**: Cria uma nova conta de Administrador.

**Request Headers**:

Authorization: Bearer <jwt_token_here>

**Request Body**:

```json
{
    "email": "paulo@exemplo.com",
    "password": "senha123",
    "confirmPassword": "senha123",
    "nome": "Nome do Proprietário",
    "cpf": "747.183.590-74"
}
```

**Response**:

- **Success**:

```json
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

### 4. Cadastrar Propriedade

**Endpoint**: `/propriedades`  
**Method**: `POST`  
**Descrição**: Cadastra uma nova Propriedade.

**Request Headers**:

Headers:
  Authorization: <jwt_token_here>

**Request Body**:

```json
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

### 5. Listar Propriedades (GET /propriedades)

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

### 6. Validar Propriedade (PUT /propriedades/validar)

**Request:**

```json
PUT /propriedades/validar
Headers:
  Authorization: Bearer <jwt_token>
Body:
{
  "id": 1,
  "status": "APROVADO",
  "mensagem": "Propriedade atende aos critérios de preservação"
}
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
    "mensagemStatus": "Propriedade atende aos critérios de preservação",
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

