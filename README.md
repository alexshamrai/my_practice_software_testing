# My Practice Software Testing

This repository contains a Maven project for practicing software testing.
It includes modules for UI tests, API tests, and common utilities.

The project was created for learning purposes and as an example for students in a Test Automation course.
The project covers the [practice-software-testing](https://github.com/testsmith-io/practice-software-testing) project with UI and API end-to-end tests.
It uses Selenide, RestAssured, and JUnit 5 frameworks.

## Modules

- **ui-tests**: Contains tests for the user interface.
- **api-tests**: Contains tests for API endpoints.
- **common**: Contains shared utilities and configurations.

## Prerequisites

- Java 21
- Maven 3.6.3 or later

## Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/my_practice_software_testing.git
    cd my_practice_software_testing
    ```

2. Install dependencies:
    ```sh
    mvn install
    ```

## Running Tests

### API Tests

To run the API tests:
```sh
mvn test -pl api-tests