# Projeto - Communication Platform

Nesse projeto foi utilizado o Vert.x por ser uma ferramenta enxuta
e não [opinável](https://vertx.io/) (ver na página sobre flexibilidade),
é amplo, suportado pelo [Eclipse Foundation](https://projects.eclipse.org/projects/rt.vertx).
Outra vantagem dessa ferramenta, é sua simplicidade,
no quesito de não ter muitas dependências externas, e ser extremamente favoravel,
ao uso de maneira otimizada dos recursos da maquina, tendo sua implementação no núcleo,
o [multi-reactor pattern](https://vertx.io/docs/vertx-core/java/),
diferente do reactor pattern comum, utilizado normalmente pelo [node.js](https://nodejs.org/en/).
O Vert.x é utilizado também pelo framework [Quarkus](https://quarkus.io/guides/vertx).

## Arquitetura do projeto

Segue um modelo parecido ao [Clean-Architecture](https://www.freecodecamp.org/news/a-quick-introduction-to-clean-architecture-990c014448d2/),
aonde as regras de negócio são separadas da implementação propriamente técnica. Sendo dividido em:

* application: Porta de entrada dos dados e saída, nesse caso, ele não é acoplado com o framework.
O framework, console ou UI, que realizam a chamada a esse módulo.
* domain: aonde contém a base das regras de negócio, como repository, service se necessário e as entidades.
* instrastructure: contém de fato o framework, nesse caso, sendo um servidor HTTP implementado com programação
não bloqueante e assíncrona.

## Mesmo desafio, porém implementado com Quarkus:

* [communication-scheduling](https://github.com/guilherme-alves-silve/communication-scheduling)

## Ambiente para execução
* [Java 11+](http://openjdk.java.net/projects/jdk/11/)
* [Docker](https://www.docker.com/)

## Tecnologias utilizadas na aplicação
* [Vert.x](https://vertx.io/)
* [PostgreSQL](https://www.postgresql.org/)
* [Lombok](https://projectlombok.org/)
* [Docker](https://www.docker.com/)
* [Google Guice](https://github.com/google/guice/)

## Tecnologias utilizadas nos testes
* [Testcontainers](https://www.testcontainers.org/)
* [Mockito](https://site.mockito.org/)
* [AssertJ](https://assertj.github.io/doc/)

## Cobertura de código
* [JaCoCo](https://www.eclemma.org/jacoco/)

## Facilidades de utilização das versões do Java

* [jabba](https://github.com/shyiko/jabba)

## Endpoints

* POST: /scheduler/
* GET: /scheduler/status/{id}
* DELETE: /scheduler/{id}

## Empacotar e executar a aplicação

Executar o docker-compose na raiz do projeto:
```
docker-compose up
```

Executar os testes:
```
./mvnw clean test
```

Empacotar a aplicação:
```
./mvnw clean package
```

Executar a aplicação em debug:
```
mvnDebug clean compile exec:java
```

Executar a aplicação:
```
./mvnw clean compile exec:java
```

## Depurar a aplicação

* [Intelijj](https://www.jetbrains.com/help/idea/run-debug-configurations-dialog.html#toolbar)
* [Eclipse](https://www.eclipse.org/community/eclipse_newsletter/2017/june/article1.php)
* [NetBeans](https://netbeans.apache.org/kb/docs/java/debug-visual_pt_BR.html)
* [VSCode](https://code.visualstudio.com/docs/java/java-debugging)

## Roadmap

* [Adicionar autenticação e autorização](https://vertx.io/blog/jwt-authorization-for-vert-x-with-keycloak/)
* [Substituir o vertx.serTimeout por NOTIFY do Redis ou mecanismo de fila](https://medium.com/nerd-for-tech/redis-getting-notified-when-a-key-is-expired-or-changed-ca3e1f1c7f0a)
* [Possivel refatoração utilizando event-bus](https://fdk.codes/you-might-not-need-dependency-injection-in-a-vertx-application/)
