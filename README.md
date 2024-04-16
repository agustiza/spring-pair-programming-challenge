# Showcase Spring Application

This is a very simple showcase Spring Boot application developed as part of a job interview. The objective of this project is to demonstrate a multi-module Maven application structure along with the usage of a Spring library with auto-configuration.

## Project Structure

The project is structured as follows:

- **app**: Acts as a minimal Spring shell entry point to the shared module.
- **car-sales-spring-starter**: Custom Spring library with auto-configuration. Contains most of the domain classes and business logic.

## Technologies Used

- **Java 21**: Programming language used for development.
- **Spring Boot**: Framework for creating stand-alone, production-grade Spring-based applications.
- **Maven**: Build automation tool used for managing dependencies and building the project.
- **H2 Database**: Lightweight in-memory database used for development and testing.
- **JUnit 5**: Testing framework used for unit and integration testing.

## Getting Started

To run the application locally, follow these steps:

1. Clone the repository:

    ```bash
    git clone https://github.com/agustiza/spring-pair-programming.git
    ```

2. Navigate to the project directory:

    ```bash
    cd spring-pair-programming
    ```

3. Build the project using Maven:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    java -jar app/target/app.jar
    ```


## License

This project is licensed under the [MIT License](LICENSE).
