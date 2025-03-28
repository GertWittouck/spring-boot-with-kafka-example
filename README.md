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


