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

## Kafka integration

### Step 1: Install Kafka (without Docker) on Mac/Linux

**Download Kafka**

```bash
wget https://downloads.apache.org/kafka/4.1.1/kafka_2.13-4.1.1.tgz
```

**Extract Kafka**

```bash
tar -xvzf kafka_2.13-4.1.1.tgz
cd kafka_2.13-4.1.1
```

**Check folder structure**

```bash
ls
# bin  config  libs  LICENSE  NOTICE  site-docs
```

### Step 2: Format Kafka Storage for KRaft (No Zookeeper)

Kafka 4.x uses KRaft mode (no Zookeeper). You need to create a cluster ID first.

```bash
# generate a cluster ID
CLUSTER_ID=$(bin/kafka-storage.sh random-uuid)
echo $CLUSTER_ID

# format the controller storage
bin/kafka-storage.sh format \
  -t $CLUSTER_ID \
  -c config/controller.properties \
  --standalone

# format the broker storage
bin/kafka-storage.sh format \
  -t $CLUSTER_ID \
  -c config/broker.properties
```

✅ Tip: Make sure log.dirs in controller.properties and broker.properties exists or Kafka will fail.

### Step 3: Start Kafka in Two Terminals

**Terminal 1 — Start the Controller**
```bash
bin/kafka-server-start.sh config/controller.properties
```

**Terminal 2 — Start the Broker**
```bash
bin/kafka-server-start.sh config/broker.properties
```

Both terminals should start without fatal errors.

### Step 4: Create a Topic

```bash
bin/kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --create \
  --topic notification.events \
  --partitions 1 \
  --replication-factor 1
```

**Check topic:**

```bash
bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
```

### Step 5: Test Kafka via Console

**Terminal 3 — Producer (send events manually)**
```bash
bin/kafka-console-producer.sh \
  --bootstrap-server localhost:9092 \
  --topic notification.events
```

Type messages:

```json
{"eventType":"USER_CREATED","userId":1,"email":"admin@noreply.com","phoneNumber":"+91 00000 00000"}
```

**Terminal 4 — Consumer (read events manually)**
```bash
bin/kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic notification.events \
  --from-beginning
```

You should see the message from Terminal 3 appear here.

### Step 6: Integrate Kafka in Microservices

#### 6a. User-Service (Producer)

`application.properties`:

```properties
spring.kafka.bootstrap-servers=127.0.0.1:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.kafka.producer.acks=all
spring.kafka.producer.retries=3
```

# Note:
* This service is hosted at https://auth.localhost:8081
* Ensure SSL is configured for secure communication. (https://github.com/vvenkatesh91Github/api-gateway/blob/master/README.md)

