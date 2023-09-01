# API Client Spring Boot Boilerplate
 *API Client Spring Boot Boilerplate* is spring boot base project.

## Application Tech Stack 
- Java 17
- Spring Boot (v3.1.2)
- Spring Data JPA
- Spring Validation
- Spring Security + JWT Token
- H2Database
- Lombok
- Swagger (Open API)
- JUnit5 + Mockito
- FlyWay

## Test Coverage
![Coverage](.github/badges/jacoco.svg)
![Branches](.github/badges/branches.svg)
  
## Run the Application

Navigate to the root of the project. For building the project using command line, run below command :

``` mvn clean package ```

Run service in command line. Navigate to *target* directory. 

``` java -jar apiclient-spring-boot-boilerplate.jar ```

## Docker build and run

`docker build --tag=apiclient-spring-boot-boilerplate:1.0 .`

`docker run -p 8080:8080 apiclient-spring-boot-boilerplate:1.0 .`

## API Doc

Api Doc(Swagger) will be served on following path;

http://localhost:8080/swagger

## License

Apache License 2.0
