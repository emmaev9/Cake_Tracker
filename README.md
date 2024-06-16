# Cake Tracker Application

This web application is designed to keep track of the birthdays of all registered people. 
The backend services are developed using Spring Boot, with Hibernate managing the communication with a PostgreSQL database.

## Backend overview

The backend follows a layered architecture, making it easy to extend and maintain. The key components include:

### Controller layer
- Handles HTTP requests related to person entities.
- Utilizes Data Transfer Objects (DTOs) to transform objects as needed.

### Service layer
- Contains the business logic for person-related operations.
- Implements an interface to define the functions, adhering to SOLID principles, specifically Dependency Inversion.

### Repository layer
- Directly interacts with the database.
- The `PersonRepository` extends `JpaRepository` to provide CRUD operations for the `Person` entity.

### Custom exceptions
- Handles situations where someone tries to register a person who is under 18 years old or already exists in the database.

## Frontend overview

The frontend is built using Vue.js, with Axios for making HTTP requests to the backend services. The structure is organized to facilitate easy extension and maintenance.

## Installation and running instructions

1. Clone the Repository
```bash
git clone https://github.com/emmaev9/Cake_Tracker
```
2. Set up the PostgreSQL: - install PostgreSQL and run it
                          - create a database named cake_tracker
3. Configure database connection in the backend: update 'application.properties' file with your PostgreSQL configurations
4. Build and run the backend from an IDE like Intellij (the application will start at port 8084)
5. Open Visual Studio Code (or other text editor)
6. Navigate to the frontend directory
7. Install the necessary dependencies
```bash
npm install
```
8. Run the server
```bash
npm run serve
```
9.Open the application in a browser at the following URL:
```bash
http://localhost:8080/
```
