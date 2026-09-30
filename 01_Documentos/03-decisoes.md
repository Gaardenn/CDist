# Decisões técnicas

- Spring Boot 4.0.3, mesma versão utilizada pelo professor nas aulas.
- Build com Maven, por maior familiaridade e por ser o mesmo padrão utilizado nas aulas.
- Idioma: entidades e endpoints em português, no plural e em minúsculas (/produtos, /categorias, /pedidos, /usuarios).
- Login por e-mail com restrição no banco, impedindo o cadastro de usuários duplicados.
- Autenticação via JWT com a biblioteca com.auth0:java-jwt, seguindo a estrutura do projeto base do professor.
- Uso do Lombok para reduzir código repetitivo (getters, setters e construtores).
- Banco de dados H2 em memória, por dispensar instalação e configuração adicionais durante o desenvolvimento.
