# Contrato da API

Método | Rota | Acesso | Obs
-|-|-|-
GET | `/categorias` | público | 200 OK
GET | `/produtos` | público |  200 OK; filtros `?categoriaId=X&page=Y&size=Z`
GET | `/produtos/{id}` | público | 200 OK; 404 não encontrado
POST | `/autenticacao/cadastro` | público | 201 criado; 400 dados inválidos; 409 conflito
POST | `/autenticacao/login` | público | 200 OK; 401 credenciais inválidas
POST | `/enderecos` | privado | 201 criado; 400 dados inválidos
GET | `/enderecos` | privado | 200 OK
POST | `/pedidos` | privado | 201 criado; 400 dados inválidos
GET | `/pedidos` | privado | 200 OK
GET | `/pedidos/{id}` | privado | 200 OK; 404 não encontrado

## Templates request e response

### GET /categorias 

#### Response

```JSON
{
    nomeCategoria: [nome da categoria]
}
```

### GET /produtos

#### Response

```JSON
{
    "nomeProduto": "[nome do produto]",
    "plataforma": "[nome da plataforma]",
    "desenvolvedora": "[nome da desenvolvedora]",
    "distribuidora": "[nome da distribuidora]",
    "status": "[status do produto (em estoque / esgotado)]",
    "midia": "[forma do produto (fisica/digital)]",
    "presente": [se é presente(true/false)],
    "quantidade": [quantidade do produto],
    "preco": [valor do produto],
    "desconto": [valor do desconto],
    "imagem": "imagem do jogo",
    "miniaturas": [
        {
            "id": [id da miniatura],
            "imagemMiniatura": "[URL da miniatura]"
        }
    ],
    "classificacao": "[classificacao indicativa]",
    "dataLancamento": "[data do lancamento]",
    "modoDeJogo": [
        {
            "id": [id do modo de jogo],
            "nomeModoDeJogo": "[nome do modo de jogo(single-player/multi-player)]"
        }
    ],
    "tags": [
        {
            "id": [id da tag do jogo],
            "nomeTag": "[nome da tag(Tiro, Plataforma, Aventura)]"
        }
    ],
    "descricao": "[descricao do jogo]",
    "avaliacao": [nota do jogo],
    "quantidadeAvaliacoes": [numero de avaliacoes]
}
```

### GET /produtos/{id}

```JSON

```

### POST /autenticacao/cadastro

```JSON

```

### POST /autenticacao/login

```JSON

```

### POST /endereco

```JSON

```

### POST /pedidos

```JSON

```

### GET /pedidos

```JSON

```