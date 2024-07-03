
# Recipe Sharing System

This is a Kotlin-based system for managing and sharing recipes. The system includes user authentication, recipe management, and recommendation features based on weather conditions.

## Technologies Used

- **Kotlin**: Primary programming language used for application development.
- **Spring Boot**: Framework for creating the REST API.
- **PostgreSQL**: Database for storing application data.
- **Flyway Migrations**: Tool for versioning relational databases.
- **Docker**: For containerizing the application and database.
- **Kubernetes**: For deploy application in testing envinroment.
- **Swagger**: For API documentation and testing.
- **JWT**: For securing endpoints and managing user authentication.

## Project Structure

```plaintext
recipe-sharing/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/
│   │   │   └── resources/
│   │   └── test/
│   └── build.gradle.kts
├── docker-postgresql/
│   ├── Dockerfile
│   └── init.sql
├── k8s/
│   ├── templates/
│   │   ├── configmap.yaml
│   │   ├── deployment.yaml
│   │   ├── service-client.yaml
│   │   └── service-headless.yaml
│   ├── Chart.yaml
│   └── values.yaml
├── documentation/
│   ├── api-docs.yaml
│   ├── api-docs.json
│   └── recipe-sharing-postman-collections.json
├── docker-compose.yml
├── .gitignore
└── README.md
```

## Local Environment

Install and configure your local environment following the manual available at [LOCAL_ENVIRONMENT.md](./LOCAL_ENVIRONMENT.md)

## Available APIs

| HTTP Method | CRUD   | Path                     | Notes                                                   | Sequence Diagram
|:------------|:-------|:-------------------------|:--------------------------------------------------------|:-----------------------------------------------
| POST        | Create | /api/v1/users            | Create a users account                                  | [view](documentation/sequence-diagrams/create-user.png)
| POST        | Read   | /auth/token              | Perform login and get an access token.                  | [view](documentation/sequence-diagrams/auth-token.png)
| GET         | Read   | /recipes/{id}            | Retrieve a specific recipe by ID.                       | [view](documentation/sequence-diagrams/get-recipe-by-id.png)
| PUT         | Update | /recipes/{id}            | Update a specific recipe.                               | [view](documentation/sequence-diagrams/update-recipe.png)
| DELETE      | Delete | /recipes/{id}            | Delete a specific recipe.                               | [view](documentation/sequence-diagrams/delete-recipe.png)
| POST        | Create | /recipes                 | Create a new recipe                                     | [view](documentation/sequence-diagrams/create-recipe.png)
| GET         | Read   | /recipes/recommendation  | Get a recipe recommendation based on weather conditions | [view](documentation/sequence-diagrams/recipe-recommendation.png)

## Sample Collections

There are some sample API calls collections in `documentation` folder.

## Diagram for Database ERD

Please see the diagram for database ERD [here](documentation/diagrams/der.png)

## Class Diagram for the Entities

The class diagram for the persistence entities is avaiable [here](documentation/diagrams/class-diagram.png)

## Models

Below are the specifications of the entities used in the system.

### UserEntity

- `id`: UUID
- `name`: String
- `username`: String
- `email`: String
- `password`: String
- `active`: Boolean
- `createdAt`: Date
- `updatedAt`: Date

### RecipeEntity

- `id`: UUID
- `title`: String
- `user`: UserEntity
- `description`: String
- `instructions`: String
- `servings`: String
- `ingredients`: List of IngredientEntity
- `createdAt`: Date
- `updatedAt`: Date

### IngredientEntity

- `id`: UUID
- `recipe`: RecipeEntity
- `value`: BigDecimal
- `unit`: IngredientUnitEnum
- `type`: String
- `createdAt`: Date
- `updatedAt`: Date

### IngredientUnitEnum

- g `Gram`
- kg `Kilogram`
- ml `Milliliter`
- l `Liter`
- pc `Piece`
- tsp `Teaspoon`
- tbsp `Tablespoon`
- pinch `A dash`

## Security

All endpoints, except for the authentication and user creation endpoints, require a valid JWT token for access. The token must be included in the `Authorization` header as a Bearer token.
