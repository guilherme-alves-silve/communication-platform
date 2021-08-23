# Projeto - Communication Platform

## Ambiente para execução
* [Java 11+](http://openjdk.java.net/projects/jdk/11/)
* [Docker](https://www.docker.com/)

## Tecnologias utilizadas na aplicação
* [Vert.x](https://vertx.io/)
* [PostgreSQL](https://www.postgresql.org/)
* [Lombok](https://projectlombok.org/)
* [Docker](https://www.docker.com/)

## Tecnologias utilizadas nos testes
* [H2](http://h2database.com/html/main.html)
* [Rest Assured](http://rest-assured.io/)

## Facilidades de utilização das versões do Java

* [jabba](https://github.com/shyiko/jabba)

## Endpoints

* POST: /scheduler/
* GET: /scheduler/status/{id}
* DELETE: /scheduler/{id}

## Swagger

```
http://localhost:8080/swagger-ui
```

## Empacotar e executar a aplicação

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
