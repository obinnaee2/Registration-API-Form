User Registration API
This is a Spring Boot application that provides a RESTful API for user registration.

Technologies Used
Java 17
Spring Boot 3.0.0
Spring Data JPA
MySQL

Setup
Clone the repository:
Copy this below :
git clone https://github.com/your-username/user-registration-api.git

Configure the database connection:
Open the application.properties file located in the src/main/resources directory.
Update the database URL, username, and password to match your MySQL server configuration.

API Documentation
The user registration API provides the following endpoints:

POST /api/register
Description: Create a new user.
Request Body:
{
  "email": "example@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "Admin",
  "organisation": "Example Organization"
}


Response:
Status: 201 Created
Body:

{
  "id": 1,
  "email": "example@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "Admin",
  "organisation": "Example Organization"
}
