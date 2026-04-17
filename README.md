# Desafio

## Contexto

Criar um formulário de cadastro de pessoas em que deve ser possível adicionar, alterar, remover e consultar pessoas.

## Estrutura do projeto

O projeto deve conter as seguintes camadas:
- **Model**: Responsável por representar a estrutura de dados da pessoa, incluindo atributos como nome, idade, email, etc.
- **Repository**: Responsável por gerenciar a persistência dos dados, permitindo adicionar,
alterar, remover e consultar pessoas.
- **Service**: Responsável por implementar a lógica de negócio, utilizando o Repository para realizar
as operações necessárias.
- **Controller**: Responsável por receber as requisições do usuário, chamar os métodos do Service e retornar as respostas adequadas.
- **Config**: Responsável pela configuração de segurança.
- **Exception**: Responsável por tratar as exceções que possam ocorrer durante a execução da aplicação, garantindo que mensagens de erro claras e informativas sejam retornadas para o usuário.
- **Util**: Responsável por conter classe utilitária destinada à validação e formatação de CPF.
- **DTO**: Responsável por conter as classes de Data Transfer Object (DTO) utilizadas para transferir dados entre as camadas da aplicação, facilitando a comunicação e garantindo a separação de responsabilidades.

## Backend

O backend foi desenvolvido em Java 17 com Spring Boot na versão 3.0.5.

Está na pasta `/teste-softplan`.

## Frontend

O frontend foi desenvolvido em React.

Está na pasta `/teste-softplan/teste-softplan-web`.

## Execução da aplicação

Para subir a aplicação, obedeça os seguintes passos:

1. Vá até a pasta `/teste-softplan` pelo terminal
2. Execute:

Rode:

```bash
docker run -p 8080:8080 -p 3000:3000 --name teste-softplan -d biangomes/teste-softplan
```

ou

```bash
docker run -p 8080:8080 biangomes/teste-softplan:latest
```

```bash
docker compose up --build
```
ou 

```bash
DOCKER_BUILDKIT=0 docker compose up --build
```

3. Acesse `http://localhost:3000` para acessar a aplicação.

## Documentação

A documentação está acessível através do Swagger, disponível em `http://localhost:8080/swagger-ui/index.html`.

Obs.: a aplicação deve estar rodando localmente.

## Collection

Na pasta `teste-softplan/artefatos` existe uma collection compatível com o Postman. Na mesma pasta, existe um arquivo markdown com exemplos de payload para utilizar.