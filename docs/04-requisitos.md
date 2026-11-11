# Requisitos

# 2 Requisitos Não Funcionais

**RNF01 - Senha nunca guardada no navegador**
A senha dos usuários nunca fica guardada no navegador e LocalStorage. Sempre deve ser enviada ao back-end e lá dentro ser salva no banco com um hash, e nunca em texto puro.

**RNF02 - Toda tela mostra carregando**
Os estados de todas as telas devem ser claros. Mostrando "Carregando", "Não Encontrado" ou "Erro".

**RNF03 - Lista paginada**
As listas de informações extensas devem ser todas paginadas pelo back-end ao chegar no front-end. Listas como Pedidos, Endereços e Produtos precisam ser paginadas.

**RNF04 - Funciona em tela de celular**
O site deve ser 100% responsivo e funcional para celulares de tamanhos variados de tela. Nesses dispositivos, todas as funções devem ser utilizáveis e o usuário não deve ter dificuldade para entender o layout.