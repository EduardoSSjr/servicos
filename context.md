Projeto SGS - Sistema de Gestão de Serviços
Tabela de Conteúdos
Visão Geral

Objetivo do Projeto

Tecnologias Utilizadas

Funcionalidades Implementadas

Arquitetura e Padrões de Projeto

Estrutura do Projeto

Exemplos de Uso dos Padrões no Código

Testes Automatizados

Como Executar o Projeto

Próximos Passos e Melhorias

1. Visão Geral
O SGS é uma aplicação web Full-Stack desenvolvida em Java com o framework Spring Boot. O sistema simula um HelpDesk para uma equipe de TI, permitindo o gerenciamento completo de Ordens de Serviço (OS), bem como o cadastro de Solicitantes e Técnicos. A aplicação foi construída com uma arquitetura em camadas (Controller, Service, Repository) e possui uma interface web simples criada com JSP (JavaServer Pages) para interação do usuário.

2. Objetivo do Projeto
Este é um projeto puramente didático, criado com os seguintes objetivos:

Aprender e aplicar na prática importantes Padrões de Projeto (Design Patterns).

Testar e demonstrar habilidades no desenvolvimento de aplicações web com Java, Spring Boot, Spring Data JPA e tecnologias relacionadas.

Servir como um objeto de estudo e material de consulta para o próprio desenvolvedor, professores e para contextualizar IAs sobre o escopo e implementação do código.

3. Tecnologias Utilizadas
Backend:

Java 21 (LTS): Linguagem de programação principal.

Spring Boot 3.3.3: Framework para criação da aplicação.

Spring Web: Para a construção da API REST e dos controllers MVC.

Spring Data JPA (Hibernate): Para a camada de persistência e comunicação com o banco de dados.

Maven: Gerenciador de dependências e build do projeto.

Frontend (View):

JSP (JavaServer Pages) & JSTL: Para a criação das páginas web dinâmicas.

Tomcat (Embarcado): Servidor de aplicação web fornecido pelo Spring Boot.

Banco de Dados:

PostgreSQL: Banco de dados relacional para persistência dos dados.

Testes:

JUnit 5 & Mockito: Para a escrita de testes de unidade e integração.

4. Funcionalidades Implementadas
A aplicação possui uma interface web que permite realizar um ciclo completo de operações (CRUD - Create, Read, Update, Delete).

Dashboard Principal:

Tela inicial que lista todas as Ordens de Serviço cadastradas.

Links de navegação para as principais ações de criação.

Gerenciamento de Ordens de Serviço (OS):

Criação de novas OS (Corretiva ou Preventiva).

Visualização de uma lista de todas as OS.

Visualização de uma página de detalhes para cada OS.

Edição dos dados de uma OS (título, descrição, tipo, prioridade).

Atribuição (ou desatribuição) de um técnico responsável.

Alteração do status do chamado (ex: de ABERTA para EM_ANDAMENTO).

Exclusão de uma OS.

Gerenciamento de Solicitantes e Técnicos:

Páginas para listar todos os Solicitantes e todos os Técnicos cadastrados.

Formulários para criar novos Solicitantes e Técnicos.

Funcionalidade para editar os dados de Solicitantes e Técnicos existentes.

Funcionalidade para excluir Solicitantes e Técnicos (com tratamento de erro caso estejam atrelados a uma OS).

5. Arquitetura e Padrões de Projeto
A aplicação segue uma arquitetura em camadas para separação de responsabilidades e utiliza os seguintes padrões de projeto:

Repository Pattern:

Como funciona: Abstrai a camada de acesso a dados. As interfaces no pacote repository (ex: OrdemDeServicoRepository) estendem a JpaRepository do Spring Data, que fornece implementações automáticas para as operações de CRUD, separando a lógica de negócio da persistência de dados.

Factory Method Pattern:

Como funciona: Usado para encapsular a lógica de criação de diferentes tipos de Ordens de Serviço. A interface OrdemDeServicoFactory define o contrato, e as classes OrdemPreventivaFactory e OrdemCorretivaFactory implementam a criação específica de cada tipo. O OrdemDeServicoService utiliza a fábrica apropriada para instanciar a OS.

Strategy Pattern (Estrutura Backend):

Como funciona: A estrutura para este padrão foi planejada para permitir diferentes formas de calcular o "esforço" ou "custo" de uma OS. A implementação completa, incluindo a interface no frontend, está planejada como um próximo passo.

Observer Pattern (Estrutura Backend):

Como funciona: O plano para este padrão é notificar as partes interessadas (como o solicitante) sempre que o status de uma OS for alterado. A estrutura básica para este padrão está contemplada no design, e sua implementação completa é um item do roadmap.

6. Estrutura do Projeto
O código-fonte está organizado nos seguintes pacotes principais dentro de br.com.sgs.servicos:

config: Classes de configuração, como o DatabaseInitializer que cria o banco de dados automaticamente.

controller: Classes que recebem as requisições HTTP (da API e da interface web).

dto: Data Transfer Objects, usados para transferir dados entre o cliente e o controller.

model: As classes de entidade que mapeiam as tabelas do banco de dados (ex: OrdemDeServico).

repository: As interfaces do Spring Data JPA para acesso ao banco.

service: Onde reside a lógica de negócio principal e a implementação dos padrões de projeto.

7. Exemplos de Uso dos Padrões no Código
Factory Method:

O padrão é orquestrado no método abrirOS da classe OrdemDeServicoService. Ele decide qual fábrica usar com base no tipo recebido.

Java

// Em OrdemDeServicoService.java
public OrdemDeServico abrirOS(String tipo, String titulo, ...) {
    OrdemDeServicoFactory factory;
    if ("preventiva".equalsIgnoreCase(tipo)) {
        factory = preventivaFactory;
    } else if ("corretiva".equalsIgnoreCase(tipo)) {
        factory = corretivaFactory;
    } else {
        throw new IllegalArgumentException("Tipo inválido");
    }
    OrdemDeServico novaOS = factory.create(titulo, descricao, ...);
    return ordemDeServicoRepository.save(novaOS);
}
Repository:

As interfaces como SolicitanteRepository são a definição do padrão.

Java

// Em repository/SolicitanteRepository.java
@Repository
public interface SolicitanteRepository extends JpaRepository<Solicitante, Long> {
}
Seu uso é feito nos controllers através de injeção de dependência (@Autowired).

Java

// Em controller/AppController.java
@Autowired
private SolicitanteRepository solicitanteRepository;

@GetMapping("/solicitantes")
public ModelAndView listarSolicitantes() {
    ModelAndView mav = new ModelAndView("solicitante/lista");
    mav.addObject("solicitantes", solicitanteRepository.findAll()); // Uso do método do repositório
    return mav;
}
8. Testes Automatizados
O projeto possui uma suíte de testes para garantir a qualidade e o funcionamento do código.

Testes de Unidade: Focados em testar uma única classe em isolamento. Exemplo: OrdemDeServicoServiceTest.java, que usa Mockito para simular (mockar) as dependências (repositórios e fábricas) e testar a lógica de negócio do serviço.

Testes de Integração: Focados em testar a interação entre várias camadas da aplicação. Exemplo: AppControllerIntegrationTest.java, que usa MockMvc do Spring para simular requisições HTTP ao controller e verificar se ele responde corretamente, interagindo com a camada de serviço e o banco de dados em memória de teste.

9. Como Executar o Projeto
9.1. Pré-requisitos
Git.

JDK 21 (LTS).

PostgreSQL.

9.2. Passos de Configuração
Clonar o Repositório:

Bash

git clone https://github.com/EduardoSSjr/servicos.git
cd servicos
Configurar a Senha do Banco:

Abra o arquivo src/main/resources/application.properties.

Altere a linha spring.datasource.password=sua_senha_aqui para a sua senha do usuário postgres.

9.3. Execução
Criação do Banco de Dados: A aplicação foi configurada para criar o banco de dados sgs_servicos automaticamente na primeira execução, caso ele não exista. Nenhuma ação manual é necessária.

Iniciar a Aplicação: Abra um terminal na raiz do projeto e execute:

PowerShell

.\mvnw spring-boot:run
9.4. Acessando a Aplicação
Após a inicialização, acesse a interface web pelo navegador no endereço: http://localhost:8080/

10. Próximos Passos e Melhorias
Este projeto didático pode ser expandido com as seguintes funcionalidades:

Frontend para Strategy e Observer: Criar interfaces para permitir que o usuário escolha diferentes formas de cálculo de esforço (Strategy) e para visualizar as notificações de mudança de status (Observer).

Segurança: Implementar Spring Security para criar um sistema de login para técnicos e solicitantes.

Testes: Aumentar a cobertura de testes, adicionando mais casos de borda e testes para a camada de controller.

Deploy: Criar um Dockerfile e um arquivo docker-compose.yml para facilitar o deploy da aplicação e do banco de dados em qualquer ambiente.

API REST Completa: Expandir a API REST (OrdemDeServicoController) para permitir que sistemas externos gerenciem o ciclo de vida das OS.