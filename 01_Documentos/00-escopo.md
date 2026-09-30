# Escopo Mínimo para Primeira Entrega

## Funcionalidades

- Lista de categorias
- Lista de produtos
- GET produto por ID
- Cadastro de usuário
- Autenticação
- Cadastro de endereços
- Cadastro de pedidos
- Lista de pedidos

## Ponderamento

| Atividade | Peso |
|-----------|------|
| Listar categorias | 0,5 |
| Listar produtos | 0,5 |
| Produto por ID | 0,5 |
| Cadastro de usuário, autenticação e autorização | 0,5 |
| Finalizar compra | 4 |
| Listar pedidos | 2 |
Prmitir cadastrar múltiplos endereços | 2 |

## Descrição

A primeira entrega precisa aplicar as funcionalidades descritas apenas por meio de
endpoints do back-end, para teste por meio do Postman. O sistema deve permitir buscar todos
produtos ofertados sem autenticação, cada produto com seus detalhes, podendo ser filtrados
por categoria. Os clientes devem poder adicionar, remover e editar os produtos no carrinho,
estando disponível até sem autenticação. Para finalizar compra deve estar cadastrado e
autenticado. Deverá ser possível também adicionar e editar endereços ao usuário específico.
Cada pedido deve ter um resumo dos itens, endereço, usuário e tipo de pagamento para ser
finalizado. O histórico de compras deve ficar atrelado ao usuário.