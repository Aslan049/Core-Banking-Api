# 🏦 Core Banking API

A RESTful Core Banking API built with **Java 17** and **Spring Boot** that simulates fundamental banking operations such as account management and financial transactions.

This project demonstrates backend development best practices including **layered architecture**, **DTO mapping with MapStruct**, **transactional operations**, **Dockerized database setup**, and **basic unit testing**.

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [API Endpoints](#-api-endpoints)
- [Transaction Model](#-transaction-model)
- [Getting Started](#-getting-started)
- [Testing](#-testing)
- [Future Improvements](#-future-improvements)
- [Author](#-author)

---

## ✨ Features

- 💳 Create and manage bank accounts
- 💰 Deposit money into accounts
- 💸 Withdraw money from accounts
- 🔁 Transfer money between accounts
- 📜 Transaction history tracking
- 🗂 DTO mapping with MapStruct
- 🛡 Global exception handling
- 🧪 Basic unit testing
- 🐳 Dockerized PostgreSQL database
- 🏗 Clean layered architecture

---

## 🛠 Tech Stack

| Technology               | Purpose                           |
|--------------------------|-----------------------------------|
| Java 17                  | Core language                     |
| Spring Boot              | Application framework             |
| Spring Data JPA          | Database access layer             |
| PostgreSQL               | Relational database               |
| MapStruct                | Entity ↔ DTO mapping              |
| Lombok                   | Boilerplate reduction             |
| Docker & Docker Compose  | Containerized database setup      |
| JUnit / Spring Boot Test | Unit & integration testing        |
| Maven                    | Build tool                        |

---

## 🏗 Architecture

The project follows a clean **layered architecture** to maintain separation of concerns:

```
┌─────────────────────────────────┐
│           Controller            │  ← Handles HTTP requests & responses
├─────────────────────────────────┤
│            Service              │  ← Business logic & transaction validation
├─────────────────────────────────┤
│           Repository            │  ← Database operations via Spring Data JPA
├─────────────────────────────────┤
│           Database              │  ← PostgreSQL
└─────────────────────────────────┘

Supporting layers:
  DTO       → Data transfer between layers
  Mapper    → MapStruct-based Entity ↔ DTO conversion
  Entity    → JPA-managed database models
  Exception → Global error handling
```

---

## 📂 Project Structure

```
src/main/java/com/example/banking/
│
├── controller/       # REST controllers — HTTP layer
├── service/          # Business logic — validation, balance updates
├── repository/       # Spring Data JPA interfaces
├── entity/           # JPA entity classes
├── dto/              # Request & response DTOs
├── mapper/           # MapStruct mappers
└── exception/        # Custom exceptions & global handler
```

---

## 📡 API Endpoints

### Accounts

| Method | Endpoint             | Description          |
|--------|----------------------|----------------------|
| POST   | `/api/accounts`      | Create a new account |
| GET    | `/api/accounts/{id}` | Get account by ID    |

### Transactions

| Method | Endpoint                      | Description                     |
|--------|-------------------------------|---------------------------------|
| POST   | `/api/transactions/deposit`   | Deposit money into an account   |
| POST   | `/api/transactions/withdraw`  | Withdraw money from an account  |
| POST   | `/api/transactions/transfer`  | Transfer money between accounts |

---

## 🔁 Transaction Model

Every financial operation creates a `Transaction` record with the following fields:

| Field         | Description                         |
|---------------|-------------------------------------|
| `id`          | Unique transaction identifier       |
| `fromIban`    | Source account IBAN                 |
| `toIban`      | Destination account IBAN            |
| `amount`      | Transaction amount                  |
| `currency`    | Currency code (e.g. USD, EUR)       |
| `type`        | `DEPOSIT` / `WITHDRAW` / `TRANSFER` |
| `status`      | `SUCCESS` / `FAILED` / `PENDING`    |
| `description` | Optional note for the transaction   |
| `createdAt`   | Timestamp of the transaction        |

---

## 🚀 Getting Started

### Prerequisites

- [Java 17+](https://adoptium.net/)
- [Docker & Docker Compose](https://www.docker.com/)
- [Maven](https://maven.apache.org/) *(or use the included `mvnw` wrapper)*

### 1. Clone the repository

```bash
git clone https://github.com/Aslan049/Core-Banking-Api.git
cd Core-Banking-Api
```

### 2. Start the PostgreSQL database

```bash
docker-compose up -d
```

This spins up a PostgreSQL container pre-configured for the application.

### 3. Run the application

```bash
./mvnw spring-boot:run
```

Or run the `main` class directly from your IDE.

The API will be available at: **`http://localhost:8080`**

---

## 🧪 Testing

The project includes basic unit tests using **JUnit** and **Spring Boot Test** to validate core service logic and banking operation behavior.

```bash
./mvnw test
```

---

## 🔐 Future Improvements

- [ ] JWT Authentication & Authorization
- [ ] Swagger / OpenAPI documentation
- [ ] Integration tests
---

## 👨‍💻 Author

**Aslan**  
Computer Engineering Student | Backend Developer (Java / Spring Boot)

[![GitHub](https://img.shields.io/badge/GitHub-Aslan049-181717?style=flat&logo=github)](https://github.com/Aslan049)
