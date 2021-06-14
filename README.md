# bank-middleware-transaction-ms
ATOS code challenge

## Getting Started

```gherkin
Feature: Transaction management 

Scenario: Receive the transaction info and store it into the system in a Happy path scenario
    Given a transaction info as input
    When call endpoint POST /transaction
    Then validate reference or generate one
    Then validate date
    Then store new transaction into the system
    Then return transaction reference

Scenario: Search transaction in a Happy path scenario
    Given acount iban and sort order as input 
    When call endpoint GET /transaction/{iban}
    Then search in ddbb filter by account iban and sort by amount in order input 
    Then return transaction list
    
Scenario: Return the status and additional information for a specific transaction in a Happy path scenario
    Given reference and client as input
    When call endpoint GET /transaction/status
    Then search transaction in ddbb
    And if reference not exists -> Business Rules A
    Then apply business rules
    Then return business rules response        
```

## Requeriments:
Java 11

## How to tun:
mvn spring-boot:run

## How to launch tests:
mvn clean test

## Api-First

Para la generación del código a partir del swagger se debe de utilizar el siguiente comando:

mvn generate-sources


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.1/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.5.1/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
