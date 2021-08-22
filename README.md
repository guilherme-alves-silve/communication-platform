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

* POST: /scheduling/
* GET: /scheduling/status/{id}
* DELETE: /scheduling/{id}

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

Executar a aplicação:
```
./mvnw clean compile exec:java
```
