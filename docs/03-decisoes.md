# Decisões técnicas

- Spring Boot 4.0.3, mesma versão utilizada pelo professor nas aulas.
- Build com Maven, por maior familiaridade e por ser o mesmo padrão utilizado nas aulas.
- Idioma: entidades e endpoints em português, no plural e em minúsculas (/produtos, /categorias, /pedidos, /usuarios).
- Login por e-mail com restrição no banco, impedindo o cadastro de usuários duplicados.
- Autenticação via JWT com a biblioteca com.auth0:java-jwt, seguindo a estrutura do projeto base do professor.
- Uso do Lombok para reduzir código repetitivo (getters, setters e construtores).
- Banco de dados H2 em memória, por dispensar instalação e configuração adicionais durante o desenvolvimento.
- Título do Pull Request seguindo padrão de commits `tipo: descrição` (feat, fix, docs, test, chore, refactor).
- Frete oficial: a API pede o frete para a logística (`logistica.url` no `application.properties`) ao criar o pedido e grava nele. Logística fora do ar ou lenta = 503 e o pedido não é criado. Pedido só com itens digitais nem chega a chamar a logística.
- Estoque: baixado no `POST /pedidos` com `UPDATE ... WHERE estoque >= quantidade` (atômico), sem vender acima do estoque em pedidos feitos ao mesmo tempo. Se faltar estoque = 409 e o pedido inteiro é recusado (rollback).

## Definition of Done

Uma tarefa só vai para "Feito" no Kanban quando:
1. Compila e os testes passam (CI verde)
2. Testado manualmente no Postman (caminho feliz + 1 erro esperado)
3. PR aprovado pelo colega
4. Endpoint documentado em docs/02-api.md (rota, exemplo de request e response)

## Retrospectiva

- Funcionou:
    - Divisão de tarefas por carga de trabalho
    - Metas bem definidas e prazos (apesar dos atrasos)
- Atrasou
    - Pouco conhecimento do assunto antes de tentar fazer efetivamente
    - Pensar demais em partes que não eram importantes para a entrega
    - Não planejar o suficiente se a parte de um era pré-requisito da de outro
- Mudar
    - Ter dailies assíncronas concretas
    - Diminuir a distância das tarefas