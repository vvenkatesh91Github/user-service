# 👤 User Service

This microservice is responsible for managing the full lifecycle of user accounts, including creation, retrieval, updating, and deletion (CRUD).

It is built using **Spring Boot** and utilizes **Flyway** for database migration management.

## 🛠️ Technology Stack

* **Framework:** Spring Boot 3.x
* **Language:** Java 17+
* **Database:** PostgreSQL (Configurable via application properties)
* **Database Migration:** Flyway
* **Data Access:** Spring Data JPA / Hibernate
* **Caching:** Memcached

---

## 🚀 Getting Started

### Prerequisites

* Java Development Kit (JDK) 17 or higher.
* Maven or Gradle build tool.
* A running PostgreSQL instance.

### Setup

1.  **Clone the Repository:**
    ```bash
    git clone [repository-url]
    cd user-service
    ```

2.  **Configure Database:**
    Update the database connection details in `src/main/resources/application.properties`:

    ```properties
    # Example PostgreSQL Configuration
    spring.datasource.url=jdbc:postgresql://localhost:5432/userdb
    spring.datasource.username=user_dev
    spring.datasource.password=password123
    
    # Flyway and JPA settings
    spring.flyway.enabled=true
    spring.jpa.hibernate.ddl-auto=none
    ```

3.  **Run the Application:**

    * **Maven:**
        ```bash
        ./mvnw spring-boot:run
        ```
    * **Gradle:**
        ```bash
        ./gradlew bootRun
        ```

### Database Migration

The service uses **Flyway** to manage schema changes. Upon startup, Flyway will automatically run all SQL scripts found in `src/main/resources/db/migration/`.

The initial migration file `V1__create_user_table.sql` will create the necessary `users` table.

---

### 🧩 Caching with Memcached

This service supports caching using **Memcached**. To enable caching, you need to install and run Memcached locally.

### Install Memcached

#### On macOS (using Homebrew):
```bash
brew install memcached
```

#### On Ubuntu/Debian:
```bash
sudo apt-get update
sudo apt-get install memcached
```

#### On Windows:
Refer to the official Memcached documentation: https://memcached.org/downloads

### Run Memcached on 127.0.0.1:11211

Start Memcached with the following command to listen on localhost (127.0.0.1) and port 11211:

```bash
memcached -l 127.0.0.1 -p 11211
```

Memcached will now be running and accessible at 127.0.0.1:11211, enabling caching for the user service.

---

## 🗺️ API Endpoints (CRUD)

The User Service exposes RESTful endpoints for user management, accessible under the base path `/users`.

| Method   | Endpoint              | Description                        |
|----------|----------------------|------------------------------------|
| POST     | `/users`             | Creates a new user account.        |
| GET      | `/users/{id}`        | Retrieves a user by ID.            |
| GET      | `/users/email/{email}` | Retrieves a user by email.         |
| GET      | `/users`             | Retrieves all users.               |
| PUT      | `/users/{id}`        | Updates a user by ID.              |
| DELETE   | `/users/{id}`        | Deletes a user by ID.              |

**Request Body Example for Creating a User:**

```json
{
  "username": "john.doe",
  "email": "john.doe@example.com",
  "password": "securepassword123"
}
```

# Note:
* This service is hosted at https://auth.localhost:8081
* Ensure SSL is configured for secure communication. (https://github.com/vvenkatesh91Github/api-gateway/blob/master/README.md)