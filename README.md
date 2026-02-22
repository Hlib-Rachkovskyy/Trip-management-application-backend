# Trip management backend application

This guide will walk you through setting up the database, environment variables, and starting the backend server.

This project is a Spring Boot application developed as part of a university course focused on Object-Oriented Programming (OOP) and UML-driven design.

Detailed logic and architectural documentation are available in the documentation folder (available in Polish only).

## Prerequisites

Maven

### 1. Database
This project requires **PostgreSQL**. You need to have a Postgres server running and accepting connections.

---

## Environment Variables

You need to create two separate `.env` files to configure the application locally.

### 1. Backend `.env`
Create a file named `.env` inside your **backend** directory:
```env
# PostgreSQL connection string
DATABASE=jdbc:postgresql://localhost:5432/postgres
# PostgreSQL username
DB_USERNAME=postgres
# PostgreSQL password
DB_PASSWORD=12345678
```
---
## Starting the Application locally

### 1. Start the Backend

Build and Install Dependencies:

```bash
./mvnw clean install
```
Start the Spring Boot server:

```bash
./mvnw spring-boot:run
```
---
## Running app via Docker
In progress
