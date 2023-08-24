# API Client Spring Boot Boilerplate

*API Client Spring Boot Boilerplate* is spring boot base project.

![Badge Under Development](http://img.shields.io/static/v1?label=STATUS&message=Under%20Development&color=orange&style=for-the-badge)

## Application Tech Stack

- Java 11
- Spring Boot (v2.7.14)
- Spring Data JPA
- H2Database
- FlyWay
- Spring Validation
- Lombok
- JUnit5 + Mockito
- Spring Security + JWT Token

## Test Coverage

![Coverage](.github/badges/jacoco.svg)
![Branches](.github/badges/branches.svg)

## Run the Application

Navigate to the root of the project. For building the project using command line, run below command :

``` mvn clean package ```

Run service in command line. Navigate to *target* directory.

``` java -jar apiclient-spring-boot-boilerplate.jar ```

## Initial Data Load to Tables

Following data added to InitialInsert file under java folder. These data is added initially when the application run.

`INSERT INTO clients (name) VALUES ('John Doe');`

`INSERT INTO clients (name) VALUES ('Jane Doe');`

## License

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
