# Spring Boot example application using Apache Kafka

## Getting started with Kafka

### Using Docker

Get the Docker image:
```
$ docker pull apache/kafka:4.0.0
```

Start the Kafka Docker container:
```
$ docker run -p 9092:9092 apache/kafka-native:4.0.0
```

This application is going to programmatically create a new topic but this can also be done
using the command line:
```
$ bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
```

## Spring for Apache Kafka

[Reference docs](https://docs.spring.io/spring-kafka/reference/)

To get started, add the following dependencies to your [pom.xml](pom.xml):
```
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency> 
```
No version has been added here as it will be inherited from the Spring Boot parent (3.4.3) in our case.

## PostgreSQL
This project makes use of [PostgreSQL](https://www.postgresql.org/). To download and install it click [here](https://www.postgresql.org/download/).

The current setup used in the project is:
* port = 5432 (default)
* username=postgres (to be setup during installation - change if needed)
* password=***** (use the password you setup during installation)

Once you have your database up and running, create a new scheme called `book-shop`:

```
-- SCHEMA: book-shop

-- DROP SCHEMA IF EXISTS "book-shop" ;

CREATE SCHEMA IF NOT EXISTS "book-shop"
    AUTHORIZATION postgres;
```

The scheme tables will be created by SpringBoot during the first starup of the application.

## Project ideas

```
Controller 
    -> create book order 
    -> persist book order into book-shop dB
    -> produce book order message
    -> consume book order message
    -> create book (acts as actual order)
    -> persist book into book-shop dB
    -> create invoice
    -> persist invoice into book-shop dB
```

### References
[integrate kafka in a spring boot application](https://medium.com/@abhishekranjandev/a-comprehensive-guide-to-integrating-kafka-in-a-spring-boot-application-a4b912aee62e)


