# Grocery Management Application
## Overview - : 
The Grocery Management Application is a RESTful Spring Boot application that allows administrators to manage grocery items and users to place orders. It provides functionalities for user authentication, grocery management, and order processing using JWT-based authentication.

## Features - :

  - Authentication & Authorization : \
        - Role-based access control (Admin/User). \
        - JWT token-based authentication. 
  - Admin Capabilities: \
        - Add, update, delete, and fetch grocery items.
  - User Capabilities: \
        - Fetch available groceries. \
        - Place orders for groceries. 

    
## Prerequisites
 Java: Version 11 or above \
 Maven: For dependency management and building the project \
 PostgreSQL: As the database \
 Docker: (Optional) For containerized deployment \
 Postman: For testing the API (Postman collection provided)


## Installation & Setup
1. Clone the Repository:

        `git clone https://github.com/gauravkhambayat/qp-assessment.git` <br>
        `cd qp-assessment`

2. Configure Database: Update the `application.properties` file with your PostgreSQL credentials :

        `spring.datasource.url=jdbc:postgresql://localhost:5432/grocery //your database name instead grocery`
        `spring.datasource.username=your_username`
        `spring.datasource.password=your_password`

3. Build the Application:
   
        `mvn clean install`

4. Run the Application:

        `mvn spring-boot:run`

4. Access the Application:

   Base URL: `http://localhost:8080`

   
## API Documentation : 

### Authentication APIs

| Endpoint | Method | Description | Sample Payload |
| --- | --- | --- | --- |
| `/auth/signup/admin` | POST | Register a new admin | `{ "username": "admin1", "password": "password123", "role": "ROLE_ADMIN" }` |
| `/auth/signup/user` | POST | Register a new user | `{ "username": "user1", "password": "password123", "role": "ROLE_USER" }` |
| `/auth/login` | POST | Login (Admin/User) | `{ "username": "admin1", "password": "password123" }` |


### Admin  APIs

| Endpoint | Method | Description | Authorization | Sample Payload |
| --- | --- | --- | --- | --- | 
| `/admin/groceries` | POST | Add a new grocery item | Bearer Token | `{ "name": "Mango", "price": 400.00, "inventory": 15, "description": "fruits" }` |
| `/admin/groceries` | GET | Fetch all grocery items | Bearer Token | None |
| `/admin/groceries/{id}` | PUT | Update grocery item by ID | Bearer Token | `{ "name": "Mango", "price": 400.00, "inventory": 15, "description": "fruits" }` |
| `/admin/groceries/{id}` | PATCH | Update inventory by ID | Bearer Token | `{ "inventory": 25 }` |
| `/admin/groceries/{id}` | DELETE | Delete grocery item by ID | Bearer Token | None |


### User  APIs

| Endpoint | Method | Description | Authorization | Sample Payload |
| --- | --- | --- | --- | --- | 
| `/user/groceries` | GET | Fetch all grocery items | Bearer Token | None |
| `/user/orders` | POST | Place an order | Bearer Token | `{ "items": [ { "itemId": "uuid1", "quantity": 2 }, { "itemId": "uuid2", "quantity": 1 } ] }` |


### Postman Collection
A Postman collection is provided in the project repository: Grocery.postman_collection.json. Import this file into Postman to test the APIs.

### Docker Build (Optional)
Ensure the maven build is done and Postgres credentials used in docker-compose.yml are correct.

Build and Run using following command

`docker-compose up --build`


