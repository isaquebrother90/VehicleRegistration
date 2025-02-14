# VehicleRegistration

## Descrição
Solução responsável por permitir registrao veículos, permitindo alterações e buscas personalizadas.

Premissas

## Tecnologias Utilizadas
- **Java 17**
- **H2 Database** (em memória)
- **Maven** (gerenciador de dependências)
- **Swagger 3** (documentação e testes de endpoints)

## Funcionalidades
- **Adiciona veículo**
- **Atualiza veículo**
- **Atualiza parcialmente um veículo**
g- **Busca Paginada**: Suporte a busca paginada para facilitar a navegação e a recuperação de dados em partes. Suporta filtro por nome, idade e cep. Para todos os tipos de usuário (ADMIN e USER).

## Pré-requisitos
- **Java 17**: Certifique-se de ter o Java 17 instalado em sua máquina.
- **Maven**: Certifique-se de ter o Maven instalado e configurado.
- **Docker**: *Caso queira utilizar essa opção para rodar a aplicação. Baixe o Docker Desktop ou outras soluções de gerenciamento de contêineres como Rancher, etc.
- **Docker Compose**: *Caso queira utilizar essa opção para rodar a aplicação. Baixando o Docker Desktop o Docker Compose já vem instalado. O Rancher vem com Rancher Compose que suporta arquivos docker-compose.yml do tipo v1 e v2.

## Configuração do Banco de Dados
A API utiliza o banco de dados H2 em memória.

## Executando a Aplicação

Disponibilizo duas formas de executar a aplicação:

- 1- Baixando e instalando ferramentas como JDK, Maven e executando comandos
- 2- Rodando um arquivo docker-compose

### 1- Baixando e instalando ferramentas como JDK, Maven e executando comandos

#### Clone o repositório:

```
git clone <URL_DO_REPOSITORIO>
cd <NOME_DO_DIRETORIO>
```

#### Compile e execute a aplicação usando Maven:


```
mvn clean install
mvn spring-boot:run
```

Acesse a aplicação em http://localhost:8080.


### 2- Rodando um arquivo docker-compose

#### Após iniciar o seu gerenciador de contêineres, no terminal, navegue até a raiz do seu projeto e execute o comando:
```
docker-compose up --build
```

## Documentação e Testes de Endpoints
A API utiliza o Swagger 3 para documentar e permitir testes dos endpoints. Após executar a aplicação, para acessar a documentação interativa, acesse http://localhost:8080/cadastra-people-doc ou http://localhost:8080/swagger-ui/index.html.

O contrato da api pode ser visualizado no projeto dentro da pasta swagger.

Para verificar os testes integrados disponíveis execute:
```
mvn test
```

### TO DO:
- Testes unitários do controlador de pessoa e da integração com api externa ViaCEP.
