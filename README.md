# 👤 User Service

This microservice is responsible for managing the full lifecycle of user accounts, including creation, retrieval, updating, and deletion (CRUD).

It is built using **Spring Boot** and utilizes **Flyway** for database migration management.

## 🛠️ Technology Stack

* **Framework:** Spring Boot 3.x
* **Language:** Java 17+
* **Database:** PostgreSQL (Configurable via application properties)
* **Database Migration:** Flyway
* **Data Access:** Spring Data JPA / Hibernate

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

## 🗺️ API Endpoints (CRUD)

The User Service exposes RESTful endpoints for user management, accessible under the base path `/api/v1/users`.

### 1. Create User (Register)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/users` | Creates a new user account. |

**Request Body Example:**

```json
{
  "username": "john.doe",
  "email": "john.doe@example.com",
  "password": "securepassword123"
}
```

# Note:
* This service is hosted https://auth.localhost:8081
* Ensure SSL is configured for secure communication. (https://github.com/vvenkatesh91Github/api-gateway/blob/master/README.md)