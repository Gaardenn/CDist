# Requisitos

## 1 Requisitos Funcionais

**RF01 - Listar produtos**
A aplicação deverá conter uma página para listar todos os produtos, utilizar como exemplo os sites de compra disponíveis na internet, a lista de produtos deve conter o nome, valor e a imagem do produto (** a imagem pode vir de uma URL externa).

**RF02 - Exibir um produto com detalhes**
A aplicação deve conter uma página para exibir um produto com detalhes, apresentando o nome, valor, descrição, imagem e botão para adicionar em uma lista de compras.

**RF03 - Carrinho de compras**
A aplicação deve conter uma página que representa o carrinho de compras, essa tela vai listar os itens adicionados na lista de compras, com a possibilidade de ajustar a quantidade dos itens adicionados e um botão para ir para tela de finalizar compra.

**RF04 - Finalizar compra**
Para finalizar a compra é necessário estar autenticado, para isso criar uma tela para cadastro de cliente (usuário) e uma tela para autenticação, clientes com o mesmo nome de usuário não devem ser permitidos.

**RF05 - Resumo de compra**
Após autenticado exibir a tela com o resumo da compra e um botão para finalizar a compra, nessa etapa os dados devem ser enviados ao servidor e a compra deve ser finalizada.

**RF06 - Lista de produtos**
A página de lista de produtos, produto individual e carrinho de compras devem ser exibidas para todos os usuários, mesmo não autenticados.

**RF07 - Listar pedidos**
Criar uma página para listar os pedidos realizados pelo usuário.

**RF08 - Filtrar produtos por categoria**
Permitir filtrar os produtos por categoria.

**RF09 - Página de produtos paginada**
Criar paginação para a página com a lista de produtos.

**RF10 - Permitir cadastro de múltiplos endereços**
Permitir o cadastro de mais de um endereço de entrega para o usuário

**RF11 - Utilizar consulta de API para obter o CEP**
Para auxiliar no preenchimento do cadastro de endereço utilizar algum serviço de consulta a Código de Endereçamento Postal (CEP) do Brasil, por exemplo o ViaCEP.

**RF12 - Calcular frete usando API externa**
Possibilitar o calculo do frete utilizando API externa.

# 2 Requisitos Não Funcionais

**RNF01 - Senha nunca guardada no navegador**
A senha dos usuários nunca fica guardada no navegador e LocalStorage. Sempre deve ser enviada ao back-end e lá dentro ser salva no banco com um hash, e nunca em texto puro.

**RNF02 - Toda tela mostra carregando**
Os estados de todas as telas devem ser claros. Mostrando "Carregando", "Não Encontrado" ou "Erro".

**RNF03 - Lista paginada**
As listas de informações extensas devem ser todas paginadas pelo back-end ao chegar no front-end. A lista de Produtos precisa ser paginada.

**RNF04 - Funciona em tela de celular**
As telas de compra devem ser usáveis em celulares de tamanhos variados de tela. Nesses dispositivos, todas as funções devem ser utilizáveis e o usuário não deve ter dificuldade para entender o layout.

# 3 Regras de Negócio

| ID | Regra | Validada em | Requisito |
| --- | --- | --- | --- |
| RN01 | Item digital não paga frete, e pedido só com itens digitais tem frete zero | Logística, `POST /frete/cotacao` | RF12 |
| RN02 | Pedido não pode ter quantidade maior que o estoque. Se faltar um item, o pedido inteiro é recusado | API, `POST /pedidos` (409) | RF05 |
| RN03 | Total e frete são calculados só no servidor | API, `PedidoService` | RF05 |
| RN04 | Endereço, preço e desconto são copiados para o pedido, e alterá-los depois não muda pedidos antigos | API, `PedidoService` | RF07 |
| RN05 | Visitante sem login monta o carrinho, que continua igual depois de recarregar a página ou fazer login | Client, carrinho no localStorage e login no `/checkout` | RF06 |
| RN06 | Não pode existir duas contas com o mesmo e-mail | API, `POST /usuarios/cadastro` (409) | RF04 |
| RN07 | O usuário só vê e usa os próprios endereços e pedidos | API, `/enderecos` e `/pedidos` (404 para dado de outro usuário) | RF07, RF10 |
