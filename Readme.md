# Desafio CRUD de usuários com MongoDB

## Objetivo

Desenvolver um web-service para gerenciamento de autenticação de usuários.

## Requisitos funcionais

 * [ ] Funcionalidades: Autenticação e gerenciamento de usuários
 * [ ] Autenticação com e-mail e senha
 * [ ] Operações: `CRUD` para dados de um usuário de sistema
   * [x] Atributos esperados: id, nome, email, senha, endereço, telefone, perfil
   * [ ] Acesso aos recursos: pesquisa com filtros, paginação e ordenação dos dados;
 * [ ] Autorização
   * [ ] Dois perfis: administrativo (`ADMIN`) e usuário padrão (`USER`);
   * [ ] Os recursos devem estar protegidos para que apenas usuários autenticados tenham acesso;
     * [ ] Adminstrador pode criar, editar e remover usuários;
     * [ ] Usuário padrão pode buscar os dados do usuário.

## Requisitos não funcionais

 * [ ] Disponibilização do código em repositório de código aberto (Github);
 * [ ] Tecnologias
   * [x] Spring Boot;
   * [ ] Spring Security;
   * [ ] MongoDB;
 * [ ] Cobertura de testes;
 * [ ] Build de container em imagem docker;
 * [ ] YAML para Kubernetes;
 * [ ] Reprodutibilidade: Documentação do processo de geração de imagem e de execução;

## Outras informações

 * [ ] Java 8;
 * [ ] Spring Boot 2.x.

## Pontos de melhoria

 * [ ] Spring Boot 3 com Java 17 ou Kotlin;
 * [ ] Criação de JREs com o mínimo necessário (necessário Java >= 9).
