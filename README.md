# FINANCE MANAGEMENT API

## Dependecies

- Java 21+ 
- Spring Boot 3.3.2
- Spring Security 6.3.1
- PostgreSQL 16+
- Spring Data JPA
- JWT API
- SWAGGER
- Maven
- Docker
- Flyway

## How to Start

<details close>
  <summary>
    <h3>
      Jar    
    </h3>
  </summary>

1. **Clone the repository:**

```
    git clone https://github.com/mamatsalay/financeapp.git
    cd financeapp
```

2. **Paste the .env file into  match-making folder**

    .env file content
   
    ```.env
    DB_URL=<your-databse-url>
    DB_USERNAME=<your-databse-username>
    DB_PASSWORD=<your-databse-password>
    ```
3. **Build the project:**

    Use Maven to build the project.

    ```sh
    mvn clean install
    ```

4. **Run the application:**

    To run the application, make sure you have Java 21 installed
    
    ```sh
    java -jar target/financeapp-0.0.1-SNAPSHOT.jar
    ```
5. **Link for the application**

   http://localhost:8080/swagger-ui/index.html#/

</details>

<details close>
  <summary>
    <h3>
      Docker    
    </h3>
  </summary>

1. **Pull the needed images**

Firstly you need to pull all the needed images from docker hub

Command to pull the database:
```shell
docker pull postgres
```

###

Command to pull the application:
```shell
docker pull mamatsalayy/dockerhub:financeapp
```

2. **Run the images**

After pulling the images you will need to run the images with following commands

PostgreSQL run command:
```shell
docker run -p <YOUR-PORT>:5432 -e POSTGRES_USER=<YOUR-DB-USERNAME> -e POSTGRES_PASSWORD=<YOUR-DB-PASSWORD> -
e POSTGRES_DB=<YOUR-DB-NAME> postgres
```

Financeapp run command:
```shell
docker run -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:<YOUR-PORT>/<YOUR-DB-NAME> -e SPRING_DATASOURCE_USERNAME=<YOUR-DB-USERNAME> -
e SPRING_DATASOURCE_PASSWORD=<YOUR-DB-PASSWORD> financeapp
```

3. **Link to use the application**

http://localhost:8080/swagger-ui/index.html#/

</details>

## How to Use

<details close>
  <summary>
    <h3>
      Steps   
    </h3>
  </summary>

  1. **Register the user**

  2. **Obtain JWT Token**

  3. **Use JWT Token when making request**

  4. **Have Fun, Good Luck :)**

</details>
