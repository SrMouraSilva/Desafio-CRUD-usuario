# Desafio CRUD de usuários com MongoDB

## Objetivo

Desenvolver um web-service para gerenciamento de autenticação de usuários.

## Organização do projeto

A documentação foi disponibilizada para consulta por meio de dois endereços 
 * Redoc: http://localhost:8080/documentation/index.html
 * Swagger-ui: http://localhost:8080/documentation/swagger-ui/index.html

### Requisitos funcionais

 * [ ] Funcionalidades: Autenticação e gerenciamento de usuários
 * [ ] Autenticação com e-mail e senha
 * [ ] Operações: `CRUD` para dados de um usuário de sistema
   * [x] Cadastro de usuário  
     * [x] Atributos esperados: id, nome, email, senha, endereço, telefone, perfil
   * [x] Busca de usuários
     * [x] pesquisa com filtros, paginação e ordenação dos dados;
   * [ ] Edição de usuário
   * [ ] Exclusão de usuário
 * [ ] Autorização
   * [ ] Dois perfis: administrativo (`ADMIN`) e usuário padrão (`USER`);
   * [ ] Os recursos devem estar protegidos para que apenas usuários autenticados tenham acesso;
     * [ ] Adminstrador pode criar, editar e remover usuários;
     * [ ] Usuário padrão pode buscar os dados do usuário.

### Requisitos não funcionais

 * [x] Disponibilização do código em repositório de código aberto (Github);
 * [ ] Tecnologias
   * [x] Spring Boot;
   * [ ] Spring Security;
   * [x] MongoDB;
 * [ ] Cobertura de testes;
 * [ ] Build de container em imagem docker;
 * [ ] YAML para Kubernetes;
 * [ ] Reprodutibilidade: Documentação do processo de geração de imagem e de execução;

### Outras restrições

 * [x] Java 8;
 * [x] Spring Boot 2.x.

## Pontos de melhoria

Como evolução desse projeto, sugere-se:

 * [ ] Spring Boot 3 com Java 17 ou Kotlin;
 * [ ] Criação de JREs com o mínimo necessário (necessário Java >= 9);
 * [ ] Autenticação:
   * [ ] OpenID Connect 1.0 por meio do [Spring Authorization Server](https://spring.io/projects/spring-authorization-server) (necessário Spring Boot 3);
   * [ ] Ou integração com IdP externa, como o Keycloak, o Amazon Cognito ou o Google Identity Provider;
 * Paginação com QueryDSL.
