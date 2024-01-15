
# Team Off - Spring Boot Day-Off Management REST API 🌅

## Overview
Team Off is a Spring Boot-based REST API designed to simplify the registration and management of day-off and vacation schedules for users and teams. The application offers authentication through email and supports OAuth2 integration with popular platforms like Google, Github, and Slack.

Effortlessly manage entire teams' schedules with specialized endpoints dedicated to retrieving all events. Whether you're planning the next sprint or determining task assignments, these endpoints provide a seamless solution for comprehensive team schedule management.

### Features

- **Efficient Schedule Management:**
    - Empowers users to easily register and manage day-off and vacation schedules.
    - Users can easily be assigned to teams, ensuring ease of use and intuitive team management
    - Endpoints that retrieve all events from teams, making it a breeze to manage entire teams schedules

- **Robust Authentication:**
    - Ensures security through multiple authentication methods.
    - `WIP` Supports email authentication and OAuth2 providers such as Google, Github, and Slack.

- **Seamless Integration:**
    - Provides a flexible and intuitive REST API.
    - Enables effortless integration with client applications, allowing developers to interact with the system with ease.
## Technologies Used

- ⚙️ Spring Boot
- 🔐 Spring Security
- 🐘 PostgreSQL
- 🔒 OAuth2
- 🚀 Hibernate
- 🛠️ Dokku
- 🐳 Docker
## Docs / Swagger
The docs to all endpoints can be found/tested here:

[api.pegorari.dev/swagger-ui/index.html](https://api.pegorari.dev/swagger-ui/index.html)


## Deploy

### Enviroment Variables

In order to run the project it's very important that you set the following enviroment variables:

`DATABASE_URL`

`POSTGRES_USER`

`POSTGRES_PASSWORD`

`JWT_SECRET`


### Using classic Maven (Linux)

In order to deploy you'll need a running postgres Database. Make sure to set the Database URL into `DATASOURCE_URL` enviroment variable or else the build will fail.

Clone the project

```bash
  git clone https://github.com/team-off-app/team-off-api.git
```

Navigate to the project folder

```bash
  cd team-off-api
  mvn clean package install
```

After `BUILD SUCESS` navigate to target folder and run the .jar

```bash
  cd target
  java -jar <name_of_jar.jar>
```
## Database Modeling

![App Screenshot](https://i.imgur.com/OZnSfTp.png)


## Author

- [@matheuspegorari](https://www.github.com/matheuspegorari)


## License

⚖️ [MIT](https://choosealicense.com/licenses/mit/)

