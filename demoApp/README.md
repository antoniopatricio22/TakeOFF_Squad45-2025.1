

# Gerenciamento de Usuários e Autenticação

##  Stack de Tecnologias
- **Java 21** | **Spring Boot 3.3.4** (Web, Security, JPA)  
- **Banco de Dados**: SQLite (padrão) ou MySQL (opcional)  
- **Autenticação**: JWT  
- **Ferramentas**: Maven, Java-Dotenv  


## Pré-requisitos

1. **Java JDK 21** ou superio
2. **Maven 3.9+**
3. Um editor de texto ou IDE (ex.: IntelliJ IDEA, VSCode, Eclipse)
4. Banco de dados (SQLite incluído; para MySQL, é necessário instalá-lo e configurá-lo)


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
SQLite (Padrão)
O banco SQLite será criado automaticamente no diretório database/ ao iniciar a aplicação.

MySQL (Opcional) modifique como no exemplo abaixo.

1 Atualize o arquivo application.properties para usar MySQL como no exemplo:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/demoApp
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

```
2 Crie um banco de dados vazio chamado demoApp no MySQL:
```bash
CREATE DATABASE demoApp;
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
        "usuario": {
            "id": 1,
            "email": "admin@exemplo.com",
            "nome": "Pedro Paulo",
            "cpf": "016.827.070-67",
            "role": "ADMINISTRADOR"
        },
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBleGVtcGxvLmNvbSIsInJvbGUiOiJST0xFX0FETUlOSVNUUkFET1IiLCJpYXQiOjE3NDM1MzcyMTMsImV4cCI6MTc0MzU1MTYxM30.EUNQ4T4tz_zzbFcBX8tGDJXkyaHM3GQ_VYrF2H4wLhOMuiwdBcT4G64m-Zd7_B81Qzy4pygpHOlEca6O8VEang"
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


Request Body:

json
```bash
{
  "email": "proprietario@exemplo.com",
  "password": "senha123",
  "confirmPassword": "senha123",
  "nome": "João Silva",
  "cpf": "424.053.410-00"
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

