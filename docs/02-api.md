# Contrato da API

Método | Rota | Acesso | Obs
-|-|-|-
GET | `/categorias` | público | 200 OK
GET | `/produtos` | público |  200 OK; filtros `?categoriaId=X&page=Y&size=Z`
GET | `/produtos/{id}` | público | 200 OK; 404 não encontrado
POST | `/usuarios/cadastro` | público | 201 criado; 400 dados inválidos; 409 conflito
POST | `/autenticacao/login` | público | 200 OK; 401 credenciais inválidas
POST | `/enderecos` | privado | 201 criado; 400 dados inválidos
GET | `/enderecos` | privado | 200 OK
POST | `/pedidos` | privado | 201 criado; 400 dados inválidos
GET | `/pedidos` | privado | 200 OK
GET | `/pedidos/{id}` | privado | 200 OK; 404 não encontrado

## Exemplos request e response

### GET /categorias 

#### Response (200)

```JSON
[
    {
        "id": 1,
        "nome": "PlayStation",
        "slug": "playstation",
        "descricao": "Console Sony, do PS1 ao PS5",
        "imagem": "https://lojavirtualcdist.vercel.app/Img-Sony-PlayStation-PNG-Image%201.webp"
    },
    {
        "id": 2,
        "nome": "Xbox",
        "slug": "xbox",
        "descricao": "Console Microsoft, do 360 ao Series X",
        "imagem": "https://lojavirtualcdist.vercel.app/Img-Xbox.webp"
    }
]
```

### GET /produtos
- Exemplo de chamada: `/produtos?categoriaId=1&page=0&size=12`

#### Response (200)

```JSON
{
    "content": [
        {
            "id": 1,
            "nome": "God of War Ragnarök",
            "plataforma": "PlayStation 4",
            "imagem": "https://lojavirtualcdist.vercel.app/God_of_War_Ragnar%C3%B6k_capa.webp",
            "preco": 187.87,
            "desconto": 0,
            "precoFinal": 187.87,
            "status": "EM_ESTOQUE"
        },
        {
            "id": 4,
            "nome": "Marvel's Spider-Man 2",
            "plataforma": "PlayStation 5",
            "imagem": "https://lojavirtualcdist.vercel.app/spider-man-2-capa.webp",
            "preco": 299.90,
            "desconto": 15,
            "precoFinal": 254.91,
            "status": "EM_ESTOQUE"
        }
    ],
    "page": {
        "size": 12,
        "number": 0,
        "totalElements": 2,
        "totalPages": 1
    }
}
```

### GET /produtos/{id}

#### Response (200)

```JSON
{
    "id": 7,
    "nome": "Super Mario Odyssey",
    "categoria": { "id": 3, "nome": "Nintendo", "slug": "nintendo" },
    "plataforma": "Nintendo Switch",
    "desenvolvedora": "Nintendo Entertainment Planning & Development",
    "distribuidora": "Nintendo",
    "midia": "DIGITAL",
    "presente": false,
    "estoque": 20,
    "preco": 349.99,
    "desconto": 0,
    "precoFinal": 349.99,
    "imagem": "https://lojavirtualcdist.vercel.app/super-mario-odyssey-capa.webp",
    "miniaturas": [
        { "id": 1, "imagemMiniatura": "https://lojavirtualcdist.vercel.app/super-mario-odyssey-1.webp" },
        { "id": 2, "imagemMiniatura": "https://lojavirtualcdist.vercel.app/super-mario-odyssey-2.webp" }
    ],
    "classificacao": "LIVRE",
    "dataLancamento": "2017-10-27",
    "modosDeJogo": [
        { "id": 1, "nome": "Single-Player" },
        { "id": 2, "nome": "Multi-Player" }
    ],
    "tags": [
        { "id": 1, "nome": "Nintendo Switch" },
        { "id": 2, "nome": "Plataforma" },
        { "id": 3, "nome": "Aventura" }
    ],
    "descricao": "Super Mario Odyssey acompanha Mario e seu novo aliado Cappy em uma aventura em 3D ao redor do mundo a bordo da nave Odyssey para resgatar a Princesa Peach de um casamento forçado com Bowser.",
    "avaliacao": 4.7,
    "quantidadeAvaliacoes": 5345,
    "distribuicaoAvaliacoes": { "5": 4120, "4": 760, "3": 280, "2": 110, "1": 75 }
}

```

### POST /usuarios/cadastro

#### Request

```JSON
{
    "nome": "Usuario 123",
    "email": "usuario@email.com",
    "senha": "Senha@123"
}
```

#### Response (201)

```JSON
{
    "message": "Usuário criado com sucesso!"
}
```

### POST /autenticacao/login

#### Request

```JSON
{
    "email": "usuario@email.com",
    "senha": "Senha@123"
}
```

#### Response (200)

```JSON
{
    "token": "...",
    "usuario": {
        "authorities": [
            {
                "autoridade": "ROLE_USER"
            }
        ],
        "email": "usuario@email.com",
        "nome": "Usuario 123"
    }
}
```

### POST /enderecos

#### Request

```JSON
{
    "cep": "85501-000",
    "logradouro": "Rua Tocantins",
    "numero": "120",
    "complemento": "Apto 2",
    "bairro": "Centro",
    "cidade": "Pato Branco",
    "estado": "PR"
}
```

#### Response (201)

```JSON
{
    "id": 1,
    "cep": "85501-000",
    "logradouro": "Rua Tocantins",
    "numero": "120",
    "complemento": "Apto 2",
    "bairro": "Centro",
    "cidade": "Pato Branco",
    "estado": "PR"
}
```

### GET /enderecos

#### Response (200)

```json
[
    {
        "id": 1,
        "cep": "85501-000",
        "logradouro": "Rua Tocantins",
        "numero": "120",
        "complemento": "Apto 2",
        "bairro": "Centro",
        "cidade": "Pato Branco",
        "estado": "PR"
    }
]
```

### POST /pedidos

#### Request

```JSON
{
    "enderecoId": 1,
    "metodoPagamento": "PIX",
    "itens": [
        { "produtoId": 4, "quantidade": 1 },
        { "produtoId": 7, "quantidade": 2 }
    ]
}
```

#### Response (201)

```JSON
{
    "id": 10,
    "data": "2026-10-06T17:45:00",
    "status": "AGUARDANDO_PAGAMENTO",
    "metodoPagamento": "PIX",
    "endereco": {
        "id": 1,
        "cep": "85501-000",
        "logradouro": "Rua Tocantins",
        "numero": "120",
        "complemento": "Apto 2",
        "bairro": "Centro",
        "cidade": "Pato Branco",
        "estado": "PR"
    },
    "itens": [
        {
            "produtoId": 4,
            "nome": "Marvel's Spider-Man 2",
            "imagem": "https://lojavirtualcdist.vercel.app/spider-man-2-capa.webp",
            "quantidade": 1,
            "precoUnitario": 254.91,
            "subtotal": 254.91
        },
        {
            "produtoId": 7,
            "nome": "Super Mario Odyssey",
            "imagem": "https://lojavirtualcdist.vercel.app/super-mario-odyssey-capa.webp",
            "quantidade": 2,
            "precoUnitario": 349.99,
            "subtotal": 699.98
        }
    ],
    "total": 954.89
}
```

### GET /pedidos

#### Response (200)

```JSON
[
    {
        "id": 10,
        "data": "2026-10-06T17:45:00",
        "status": "ENTREGUE",
        "quantidadeItens": 3,
        "total": 954.89
    }
]
```

### GET /pedidos/{id}

#### Response (200)

```JSON
{
    "id": 10,
    "data": "2026-10-06T17:45:00",
    "status": "ENTREGUE",
    "metodoPagamento": "PIX",
    "endereco": {
        "id": 1,
        "cep": "85501-000",
        "logradouro": "Rua Tocantins",
        "numero": "120",
        "complemento": "Apto 2",
        "bairro": "Centro",
        "cidade": "Pato Branco",
        "estado": "PR"
    },
    "itens": [
        {
            "produtoId": 4,
            "nome": "Marvel's Spider-Man 2",
            "imagem": "https://lojavirtualcdist.vercel.app/spider-man-2-capa.webp",
            "quantidade": 1,
            "precoUnitario": 254.91,
            "subtotal": 254.91
        },
        {
            "produtoId": 7,
            "nome": "Super Mario Odyssey",
            "imagem": "https://lojavirtualcdist.vercel.app/super-mario-odyssey-capa.webp",
            "quantidade": 2,
            "precoUnitario": 349.99,
            "subtotal": 699.98
        }
    ],
    "total": 954.89
}
```