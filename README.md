Cake Tracker Application

This is a web application developed for keeping track of birthdays of all the people registered. 
For creating the backend services I used Spring Boot, the communication with the database (PostgreSQL) being handled wth Hibernate.
The structure of the database follows a layerd architecture. I created packages for each layer to be easy to extand.
## The controller layer handles HTTP requests related to person entitie. The objects are transformed as needed using DTOs.
## The service layer contains the bussines logic for the person related operations. I also added an interface to define the functions and to respect the SOLID principles (Dependency Inversion)
## The repository layer interact with the database directy. I have a Person Repository which extands JPARepository to provide CRUD operations for the Person entity.

I also have a package with custom exceptions to handle situations where someone is trying to register a person that doens't have at least 18 yeart old or the person is already in the database.

The frontend was build using Vue.js with Axios for making HTTP requests to the backend services.
The frontend is structures too so it can be easily extended.

Instalation and running instructions
1. Clone this repository: git clone https://github.com/emmaev9/Cake_Tracker
2. Set up the PostgreSQL: - install PostgreSQL and run it
                          - create a database named cake_tracker
3. Configure database connection in the backend: update 'application.properties' file with your PostgreSQL configurations
4. Build and run the backend from an IDE like Intellij (the application will start at port 8084)
5. Open Visual Studio Code (or other text editor)
6. Navigate to the frontend directory
7. Install the necessary dependencies: npm install
8. Run the server:  npm run serve
9. Open the application in a brower with the following path: http://localhost:8080/
