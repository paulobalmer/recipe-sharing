
# Local Development Setup

## Prerequisites

- Docker
- Docker Compose
- JDK 11 or higher

## Setting Up the Database

1. Navigate to the project directory:

    ```bash
    cd recipe-sharing
    ```

2. Start the PostgreSQL database using Docker Compose:

    ```bash
    docker-compose up -d
    ```

3. Verify that the database is running:

    ```bash
    docker ps
    ```

## Setting Up the Application

1. Navigate to the `app` directory:

    ```bash
    cd app
    ```

2. Build the application:

    ```bash
    ./gradlew build
    ```

3. Run the application:

    ```bash
    ./gradlew bootRun
    ```

4. Verify that the application is running by navigating to `http://localhost:8080` in your browser.

## Environment Variables

Ensure the following environment variables are set:

- `DB_HOST`: The host for the PostgreSQL database (e.g., `localhost`)
- `DB_PORT`: The port for the PostgreSQL database (e.g., `5432`)
- `DB_NAME`: The name of database in PostgreSQL (e.g., `recipe`)
- `DB_USERNAME`: The username for the database (default is `postgres`)
- `DB_PASSWORD`: The password for the database (default is `password`)
- `WEATHER_API_KEY`: The API Key for consuming Weather API from `https://www.visualcrossing.com/`

## Swagger Documentation

Access the API documentation at `http://localhost:8080/swagger-ui.html`.

The OpenAPI files in json and yaml format are avaiable in `documentation` folder as well.

## Troubleshooting

- If the application fails to start, check the logs for errors.
- Ensure that Docker is running and the PostgreSQL container is up.
- Verify that the environment variables are correctly set.
