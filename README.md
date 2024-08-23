# FINANCE MANAGEMENT API

## Dependecies

- Java 21+
- Spring Boot 3.3.2
- Spring Security 6.3.1
- PostgreSQL 16+
- Spring Data JPA
- JWT API
- SWAGGER

## How to Start

<details close>
  <summary>
    <h3>
      Jar    
    </h3>
  </summary>

  1. **Clone the repository:**

    git clone https://github.com/mamatsalay/financeapp.git
    cd financeapp

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

   http://localhost/swagger-ui/index.html#/

</details>
