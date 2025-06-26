
# 📜 Transactions API

A microservice for managing transaction routines.

Built with:
-  Java 21
- Spring Boot 3.2.5
-  PostgreSQL
-  Clean Architecture
- Tested with JUnit 5
---

##  Features

- ✅ Create an account.
- ✅ Retrieve the account information.
- ✅ Create a transaction.

---

## 🗂️ Project Structure (Clean Architecture)

```
src
├── main
│   ├── java
│   │   └── dev.mmartins.transactionapi
│   │       ├── domain          → Entities & Repositories (Enterprise Business Rules)
│   │       ├── application     → Use Cases (Application Business Rules)
│   │       ├── infrastructure  → PostgreSQL Adapters
│   │       └── entrypoint      → REST Controllers
├── test
│   ├── java
│   └── dev.mmartins.transactionapi → Unit & Integration tests
│       ├── unit           
│       └── integration
```

---

## Tech Stack

| Tool        | Version | Description                     |
|-------------|---------|---------------------------------|
| Java        | 21      | Runtime                         |
| Spring Boot | 3.2.5   | Framework                       |
| PostgreSQL  | 16      | Database                        |
| JUnit       | 5       | Test Framework                  |
| Springdoc   | 2.3.0   | OpenAPI / Swagger UI            |
| Gradle      | Latest  | Build Tool                      |

---

## Running the App

### Prerequisites
- Java 21
- PostgreSQL running on `localhost:5432`
- Gradle installed (or use `./gradlew`)

###  Run the app:
Note: You will need to have a PostgreSQL, where you can follow this steps:

```bash
docker run --name postgres-db \
-e POSTGRES_DB=transactions-api \
-e POSTGRES_USER=root \
-e POSTGRES_PASSWORD=1234 \
-d postgres
```

or just run the docker-compose file with `./run-app.sh`
```bash
./run-app.sh
```
---

Run the app:
```bash
./gradlew bootRun
```

---

## 🔗 API Documentation (Swagger)

Once the app is running:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Running Tests

###  Run unit, integration, and BDD tests:

```bash
./gradlew clean test
```


## API Endpoints Example

| Method | Endpoint         | Description                      |
|--------|------------------|----------------------------------|
| POST   | `/accounts`      | Create an account                |
| GET    | `/accounts/{id}` | Retrieve the account information |
| POST   | `/transactions`  | Create a transaction             |

---


##  Contributions

Pull requests are welcome.

---
