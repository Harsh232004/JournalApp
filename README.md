# 📓 JournalApp API

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)]()
[![License](https://img.shields.io/badge/license-MIT-blue)]()
[![Java](https://img.shields.io/badge/java-17-orange)]()

A RESTful backend built with **Spring Boot** that allows users to create, read, update and delete personal journal entries. Designed for easy integration with web/mobile frontends and secure user authentication.

---

## 🚀 Table of Contents
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Clone & Run (local)](#clone--run-local)
  - [Using Docker](#using-docker)
- [Configuration](#-configuration)
- [API Overview](#-api-overview)
- [Authentication](#-authentication)
- [Examples (curl)](#-examples-curl)
- [Testing](#-testing)
- [Contributing](#-contributing)
- [Troubleshooting](#-troubleshooting)
- [License](#-license)
- [Contact / Support](#-contact--support)

---

## ✨ Features
- User registration & login (JWT authentication)
- Create / Read / Update / Delete journal entries
- Entry tagging and search by keywords/date
- Pagination and sorting on list endpoints
- Role-based access (user, admin) — optional
- API docs via Swagger / OpenAPI

---

## 🧰 Tech Stack
- Java 17
- Spring Boot 3.x
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- PostgreSQL (or MySQL)
- Maven (or Gradle)
- Docker (optional)
- Swagger UI (OpenAPI)

---

## 🛠 Getting Started

### Prerequisites
- Java 17+ (JDK)
- Maven or Gradle
- PostgreSQL (or MySQL)
- Docker & Docker Compose (optional)

### Clone & Run (local)
```bash
git clone https://github.com/your-username/journalapp.git
cd journalapp

# build
./mvnw clean package

# run
./mvnw spring-boot:run
