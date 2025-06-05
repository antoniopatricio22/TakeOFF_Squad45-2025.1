![thumbnail](https://recifevirado.recife.pe.gov.br/wp-content/uploads/2021/10/logo-embarque-digital-1.png)




---
# Apresentação
Repositório do Projeto do Squad 45 (UNIT), para Desafio proposto pela BBTS e Porto Digital como parte da Residência Tecnológica do Programa Embarque Digital Criado pela Prefeitura da Cidade do Recife, PE - Brasil . 

# Desafio

O projeto visa resolver o problema enfrentado por grandes agricultores em relação às áreas de 
reserva ambiental obrigatórias. Atualmente, esses agricultores são obrigados a manter áreas preservadas, 
sem poder desmatá-las para torná-las produtivas, sob pena de enfrentar multas pesadas. O objetivo é criar 
um modelo de incentivo por meio da tokenização dessas áreas preservadas, permitindo que o agricultor seja 
remunerado pela manutenção da reserva ambiental. A moeda digital será gerada conforme a quantidade de 
carbono sequestrado pela área de reserva. 
O Documento do desafio completo se emcontra neste link: [Formalização de demanda - Tokenização de Reservas Ambientais (PDF)](./Docs/Fomalização%20de%20demanda%20-%20Tokenização%20de%20Reservas%20Ambientais.pdf)


---

# instruções para Implementação
# Passo a Passo para execução do MVP

Abaixo está o guia completo para implantar e executar a solução do zero em um novo ambiente.

## 1. Pré-requisitos

- **Java JDK 21** ou superior
- **Maven 3.9+**
- **PostgreSQL** (banco de dados relacional)
- **Git** (para clonar o repositório)
- **Editor de código** (VSCode, IntelliJ, etc.)

## 2. Clonando o Repositório

Abra o terminal e execute:

```bash
git clone https://github.com/antoniopatricio22/TakeOFF_Squad45-2025.1.git
cd TakeOFF_Squad45-2025.1
```

## 3. Configurando o Banco de Dados

1. Instale o PostgreSQL e crie um banco de dados chamado `demoapp` (ou outro nome de sua preferência).
2. Anote o usuário e senha do banco para configurar no backend.

## 4. Configurando o Back-end

O back-end está localizado na pasta [`demoApp`](./demoApp).  
Siga o passo a passo detalhado no arquivo [`demoApp/README.md`](./demoApp/README.md), que inclui:

- Como configurar variáveis de ambiente (`.env`)
- Como ajustar o arquivo `application.properties` para o PostgreSQL
- Como compilar e rodar o projeto localmente ou em nuvem (Render)
- Como acessar a API e exemplos de uso dos endpoints


## 5. Executando o Front-end

O front-end é composto por arquivos HTML, CSS e JS presentes na raiz do projeto ou em subpastas como `html/`, `css/` e `scripts/`.

### Passos para rodar o front-end:

1. **Abra o arquivo `index.html`** (ou a landing page principal) em seu navegador.
2. **Certifique-se de que o back-end está rodando** (localmente ou na nuvem) e que a URL da API está correta no arquivo [`scripts/config.js`](./scripts/config.js).
   - Por padrão, a URL aponta para o ambiente de produção. Para rodar localmente, altere para `http://localhost:8080` se necessário.
3. **Utilize as telas de login, cadastro e funcionalidades normalmente.**

## 6. Testando a Solução

- Cadastre um proprietário ou administrador.
- Realize login.
- Cadastre propriedades e utilize as funções de validação conforme o fluxo do desafio.



---



Take_Off: Squad45-Unit.

Integrantes:
- Antônio Patrício Macena de Barros
- Charles Bartolomeu da Silva Santos
- Gabriel Oliveira Parisio
- Giovanna Rodrigues Brandão Viana
- João Ricardo Queiroz Calado
- Lucas de Araújo dos Prazeres
- Nicholas Matheus Silva de Araújo
- Paulo Thiago Santos da Silva
- Zayra Manuelle Barros Meireles

Mentores:
- Eryson Moreira - PD
- Kelsen Brito - BBTS
- Guilherme Alves


Recife, Junho de 2025.

---