# Contrato da API de Logística

Serviço separado da API principal. Consulta CEP (pelo ViaCEP) e faz cotação de frete.

- Pasta: `logistica/`
- Porta: `8081`
- URL base: `http://localhost:8081`
- Acesso: público
- CORS liberado para `http://localhost:5173`
- Erros com o mesmo tipo de dado da API principal

| Método | Rota | Acesso | Obs |
|--------|------|--------|-----|
| GET    | `/cep/{cep}` | público | 200 OK; 400 CEP mal formado; 404 CEP inexistente; 502 ViaCEP indisponível |
| POST   | `/frete/cotacao` | público | 200 OK; 400 dados inválidos ou CEP mal formado; 404 CEP inexistente; 502 ViaCEP indisponível |

## Exemplos request e response

### GET /cep/{cep}

- Pode ser `01001000` ou `01001-000`
- A resposta devolve o CEP apenas com dígitos
- A consulta ao ViaCEP tem timeout de 2 segundos
- Exemplo de chamada: `/cep/01001000`

#### Response (200)

```JSON
{
    "cep": "01001000",
    "logradouro": "Praça da Sé",
    "bairro": "Sé",
    "cidade": "São Paulo",
    "estado": "SP"
}
```

Obs: CEPs de cidades pequenas podem vir com `logradouro` e `bairro` vazios (`""`)

#### Response (400) - CEP mal formado

```JSON
{
    "timestamp": 1792000000000,
    "status": 400,
    "message": "CEP inválido. Informe 8 dígitos, com ou sem hífen.",
    "url": "/cep/abc123",
    "validationErrors": null
}
```

#### Response (404) - CEP bem formado, mas inexistente

```JSON
{
    "timestamp": 1792000000000,
    "status": 404,
    "message": "CEP não encontrado",
    "url": "/cep/99999999",
    "validationErrors": null
}
```

#### Response (502) - ViaCEP fora do ar, lento ou com resposta inválida

```JSON
{
    "timestamp": 1792000000000,
    "status": 502,
    "message": "Serviço de consulta de CEP indisponível",
    "url": "/cep/01001000",
    "validationErrors": null
}
```

### POST /frete/cotacao

#### Request

```JSON
{
    "cepDestino": "01001000",
    "itensFisicos": 3
}
```

| Campo | Tipo | Obrigatório | Regra |
|-------|------|-------------|-------|
| cepDestino | String | Sim  | 8 dígitos, com ou sem hífen |
| itensFisicos | Integer | Sim | 0 ou mais. É a soma das quantidades dos itens de mídia física |

#### Response (200)

```JSON
{
    "valor": 26.00,
    "prazoDias": 6,
    "regiao": "Sudeste"
}
```

#### Response (200) - sem item físico

Não consulta o ViaCEP, mas o `cepDestino` ainda precisa ser válido.

```JSON
{
    "valor": 0.00,
    "prazoDias": 0,
    "regiao": null
}
```

#### Response (400) - dados inválidos

```JSON
{
    "timestamp": 1792000000000,
    "status": 400,
    "message": "Erro de validação!",
    "url": "/frete/cotacao",
    "validationErrors": {
        "itensFisicos": "A quantidade de itens físicos não pode ser negativa"
    }
}
```

Os erros 404 e 502 têm o mesmo formato do `GET /cep/{cep}`

## Regras de cálculo

`valor = base + adicional * (itensFisicos - 1)`, sempre com 2 casas decimais

A região vem da UF que vem da resposta do ViaCEP. A origem da loja é Pato Branco

| Região | UFs | Base | Adicional | Prazo |
|--------|-----|------|-----------|-------|
| Sul    | PR, SC, RS | R$ 12,00 | R$ 3,00 | 3 dias |
| Sudeste | SP, RJ, MG, ES | R$ 18,00 | R$ 4,00 | 5 dias |
| Centro-Oeste | DF, GO, MT, MS | R$ 24,00 | R$ 5,00 | 7 dias |
| Nordeste | BA, SE, AL, PE, PB, RN, CE, PI, MA | R$ 30,00 | R$ 6,00 | 9 dias |
| Norte | AC, AM, AP, PA, RO, RR, TO | R$ 36,00 | R$ 7,00 | 12 dias |

Exemplos: 3 itens no Sul = 12 + 3x2 = R$ 18,00. 5 itens no Norte = 36 + 7x4 = R$ 64,00

- RN01: item digital não paga frete. Pedido só com itens digitais tem `valor` 0, `prazoDias` 0 e `regiao` `null`
- O valor oficial do frete é gravado pela API principal quando cria o pedido (RN03). Esta cotação serve para exibir no cliente

## Como rodar

```bash
cd logistica
mvnw.cmd spring-boot:run  # sobe na porta 8081
mvnw.cmd test
```

Configuração em `logistica/src/main/resources/application.properties`