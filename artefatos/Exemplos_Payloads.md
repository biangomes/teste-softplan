# 📮 Exemplos de Payloads - Teste Softplan

## Versão 1 - Exemplos

### ✅ Exemplo V1 - Mínimo (Apenas campos obrigatórios)

```json
{
  "nome": "João Silva",
  "cpf": "12345678901",
  "dataNascimento": "15/05/1990"
}
```

### ✅ Exemplo V1 - Completo (Todos os campos)

```json
{
  "nome": "João Silva",
  "cpf": "12345678901",
  "sexo": "H",
  "email": "joao@example.com",
  "dataNascimento": "15/05/1990",
  "estado": "SP",
  "pais": "Brasil"
}
```

### ✅ Exemplo V1 - Mulher

```json
{
  "nome": "Maria Santos",
  "cpf": "98765432100",
  "sexo": "M",
  "email": "maria.santos@example.com",
  "dataNascimento": "20/03/1988",
  "estado": "RJ",
  "pais": "Brasil"
}
```

### ✅ Exemplo V1 - Sem Sexo e Email

```json
{
  "nome": "Pedro Costa",
  "cpf": "55555555555",
  "dataNascimento": "10/01/1985",
  "estado": "MG",
  "pais": "Brasil"
}
```

---

## Versão 2 - Exemplos

### ✅ Exemplo V2 - Completo (Todos os campos)

```json
{
  "nome": "Ana Clara",
  "cpf": "11122233344",
  "sexo": "M",
  "email": "ana.clara@example.com",
  "dataNascimento": "25/07/1992",
  "estado": "SP",
  "pais": "Brasil",
  "endereco": {
    "cidade": "São Paulo",
    "rua": "Avenida Paulista",
    "numero": 1000,
    "cep": "01311-100",
    "bairro": "Bela Vista",
    "complemento": "Apartamento 456"
  }
}
```

### ✅ Exemplo V2 - São Paulo

```json
{
  "nome": "Carlos Eduardo",
  "cpf": "44455566677",
  "sexo": "H",
  "email": "carlos.edu@example.com",
  "dataNascimento": "12/06/1990",
  "estado": "SP",
  "pais": "Brasil",
  "endereco": {
    "cidade": "São Paulo",
    "rua": "Rua Augusta",
    "numero": 2500,
    "cep": "01305-100",
    "bairro": "Centro",
    "complemento": "Sala 100"
  }
}
```

### ✅ Exemplo V2 - Rio de Janeiro

```json
{
  "nome": "Fernanda Oliveira",
  "cpf": "77788899900",
  "sexo": "M",
  "email": "fernanda@example.com",
  "dataNascimento": "30/11/1987",
  "estado": "RJ",
  "pais": "Brasil",
  "endereco": {
    "cidade": "Rio de Janeiro",
    "rua": "Avenida Copacabana",
    "numero": 3000,
    "cep": "22020-001",
    "bairro": "Copacabana",
    "complemento": "Bloco A, Apartamento 1005"
  }
}
```

### ✅ Exemplo V2 - Sem Complemento

```json
{
  "nome": "Roberto Silva",
  "cpf": "12312312312",
  "sexo": "H",
  "email": "roberto@example.com",
  "dataNascimento": "05/02/1985",
  "estado": "MG",
  "pais": "Brasil",
  "endereco": {
    "cidade": "Belo Horizonte",
    "rua": "Avenida Brasil",
    "numero": 500,
    "cep": "30140-071",
    "bairro": "Centro",
    "complemento": ""
  }
}
```

### ✅ Exemplo V2 - Mínimo (Apenas obrigatórios)

```json
{
  "nome": "Lucas Mendes",
  "cpf": "45645645645",
  "dataNascimento": "18/08/1993",
  "endereco": {
    "cidade": "Salvador",
    "rua": "Rua Primeiro de Maio",
    "numero": 150,
    "cep": "40110-160",
    "bairro": "Comercio",
    "complemento": ""
  }
}
```

### ✅ Exemplo V2 - Sem Email e Estado

```json
{
  "nome": "Juliana Costa",
  "cpf": "99988877766",
  "sexo": "M",
  "dataNascimento": "22/09/1991",
  "pais": "Brasil",
  "endereco": {
    "cidade": "Brasília",
    "rua": "Esplanada dos Ministérios",
    "numero": 100,
    "cep": "70050-900",
    "bairro": "Centro",
    "complemento": "Bloco Norte"
  }
}
```

---

## Casos de Uso e Testes

### 📝 Teste 1: Criar e Editar em V1

**Criar:**
```json
{
  "nome": "Teste User",
  "cpf": "11111111111",
  "dataNascimento": "01/01/2000",
  "email": "teste@example.com"
}
```

**Editar (mude alguns dados):**
```json
{
  "nome": "Teste User Editado",
  "cpf": "11111111111",
  "dataNascimento": "01/01/2000",
  "email": "teste.novo@example.com"
}
```

---

### 📝 Teste 2: Criar e Editar em V2

**Criar:**
```json
{
  "nome": "Test V2 User",
  "cpf": "22222222222",
  "dataNascimento": "02/02/2001",
  "endereco": {
    "cidade": "São Paulo",
    "rua": "Rua Teste",
    "numero": 999,
    "cep": "00000-000",
    "bairro": "Teste",
    "complemento": "Casa de Teste"
  }
}
```

**Editar (mude alguns dados):**
```json
{
  "nome": "Test V2 User Editado",
  "cpf": "22222222222",
  "dataNascimento": "02/02/2001",
  "endereco": {
    "cidade": "Rio de Janeiro",
    "rua": "Rua Teste Editada",
    "numero": 888,
    "cep": "11111-111",
    "bairro": "Teste Novo",
    "complemento": "Apto 123"
  }
}
```

---

### 📝 Teste 3: Múltiplas Pessoas V1

**Pessoa 1:**
```json
{
  "nome": "Alice Costa",
  "cpf": "33333333333",
  "dataNascimento": "15/03/1995"
}
```

**Pessoa 2:**
```json
{
  "nome": "Bob Silva",
  "cpf": "44444444444",
  "dataNascimento": "20/05/1998"
}
```

**Pessoa 3:**
```json
{
  "nome": "Charlie Santos",
  "cpf": "55555555555",
  "dataNascimento": "10/07/2000"
}
```

---

### 📝 Teste 4: Múltiplas Pessoas V2

**Pessoa 1:**
```json
{
  "nome": "Diana Mendes",
  "cpf": "66666666666",
  "dataNascimento": "12/04/1996",
  "endereco": {
    "cidade": "Salvador",
    "rua": "Rua A",
    "numero": 100,
    "cep": "40000-000",
    "bairro": "Bairro A",
    "complemento": "Casa 1"
  }
}
```

**Pessoa 2:**
```json
{
  "nome": "Ethan Oliveira",
  "cpf": "77777777777",
  "dataNascimento": "25/06/1997",
  "endereco": {
    "cidade": "Recife",
    "rua": "Rua B",
    "numero": 200,
    "cep": "50000-000",
    "bairro": "Bairro B",
    "complemento": "Casa 2"
  }
}
```

**Pessoa 3:**
```json
{
  "nome": "Fiona Rocha",
  "cpf": "88888888888",
  "dataNascimento": "30/08/1999",
  "endereco": {
    "cidade": "Fortaleza",
    "rua": "Rua C",
    "numero": 300,
    "cep": "60000-000",
    "bairro": "Bairro C",
    "complemento": "Casa 3"
  }
}
```

---

## CPFs para Testes

Alguns CPFs que você pode usar (estes não são CPFs reais):

```
V1 Testes:
- 11111111111
- 22222222222
- 33333333333
- 44444444444
- 55555555555

V2 Testes:
- 66666666666
- 77777777777
- 88888888888
- 99999999999
- 12121212121

Mistos:
- 10101010101
- 20202020202
- 30303030303
```

⚠️ **Importante:** CPFs reais serão validados pelo backend. Se precisar testar com CPF válido, use CPFs reais (se você tiver permissão).

---

## Datas para Testes

Algumas datas que você pode usar:

```
Passado:
- 01/01/1980 (40+ anos)
- 15/05/1990 (30+ anos)
- 10/03/1995 (25+ anos)
- 20/07/2000 (20+ anos)

Recente:
- 05/12/2005 (18+ anos)
- 25/06/2010 (14+ anos)
- 30/11/2015 (9+ anos)
```

---

## Cidades Brasileiras para V2

```
São Paulo - SP:
- Avenida Paulista
- Rua Augusta
- Avenida Brasil
- Rua Oscar Freire

Rio de Janeiro - RJ:
- Avenida Copacabana
- Rua Ataulfo de Paiva
- Avenida Vieira Souto
- Rua Visconde de Piraja

Minas Gerais - MG:
- Avenida Brasil
- Rua da Bahia
- Avenida Getúlio Vargas
- Rua Espírito Santo

Bahia - BA:
- Avenida 7 de Setembro
- Rua Chile
- Avenida Oceânica
- Rua Primeiro de Maio

CEPs Válidos:
- SP: 01311-100, 05429-020, 03026-000
- RJ: 22020-001, 20040-020, 22250-040
- MG: 30140-071, 31140-000, 30130-100
```

---

## ✅ Casos de Sucesso Esperados

### V1 - Respostas Esperadas

**POST /pessoas/v1/ - 201 Created**
```json
{
  "id": 1,
  "nome": "João Silva",
  "cpf": "12345678901",
  "sexo": "H",
  "email": "joao@example.com",
  "dataNascimento": "15/05/1990",
  "estado": "SP",
  "pais": "Brasil",
  "endereco": null,
  "dataCriacao": "2026-04-16T23:00:00",
  "dataAtualizacao": "2026-04-16T23:00:00"
}
```

**GET /pessoas/v1/ - 200 OK**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    ...
  }
]
```

### V2 - Respostas Esperadas

**POST /api/v2/pessoas/ - 201 Created**
```json
{
  "id": 1,
  "nome": "Ana Clara",
  "cpf": "11122233344",
  "sexo": "M",
  "email": "ana.clara@example.com",
  "dataNascimento": "25/07/1992",
  "estado": "SP",
  "pais": "Brasil",
  "endereco": {
    "cidade": "São Paulo",
    "rua": "Avenida Paulista",
    "numero": 1000,
    "cep": "01311-100",
    "bairro": "Bela Vista",
    "complemento": "Apartamento 456"
  },
  "dataCriacao": "2026-04-16T23:00:00",
  "dataAtualizacao": "2026-04-16T23:00:00"
}
```

---

## 🎯 Fluxo de Teste Recomendado

1. **Criar 3 pessoas em V1**
2. **Listar em V1** (verifique se estão lá)
3. **Editar 1 pessoa em V1**
4. **Deletar 1 pessoa em V1**
5. **Criar 3 pessoas em V2** (com endereço)
6. **Listar em V2** (verifique endereços)
7. **Buscar 1 pessoa em V2** (por ID)
8. **Editar 1 pessoa em V2**
9. **Deletar 1 pessoa em V2**
10. **Listar em V1** (verifique compatibilidade)
11. **Listar em V2** (verifique compatibilidade)

---

## 💡 Dicas Finais

- Copie um exemplo e cole no Postman
- Mude apenas os valores que deseja testar
- Use nomes descritivos (deixa mais fácil entender depois)
- Teste com dados extremos (vazio, muito grande, caracteres especiais)
- Sempre capture os IDs retornados para usar em testes subsequentes

---

**Sucesso nos testes!** 🚀

