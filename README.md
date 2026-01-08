# JournalEntryApp

A Spring Boot application for managing personal journal entries with secure authentication and role-based access control.

## Project Description

**JournalEntryApp** is developed using Spring Boot 2.7.x, focusing on **secure authentication and authorization** using **JWT** and **OAuth2**. It implements **role-based access control** so that only authorized users can access their own entries.  

The application supports full **CRUD operations** for journal entries and includes **scheduled tasks using Cron** for automated email notifications. Data is stored efficiently and scalably using **MongoDB Atlas**. The project also uses **Redis** for caching/session management and **Kafka** for messaging between services.

---

## Features

- **User Authentication & Authorization** using JWT and OAuth2  
- **Role-Based Access Control** (Admin/User)  
- **CRUD Operations** for journal entries  
- **Scheduled Tasks** for automated email notifications  
- **MongoDB Atlas Integration** for scalable database management   
- **Kafka Messaging** for event-driven architecture  
- REST API endpoints for integration with front-end or other services  

---

## Technologies Used

- Java 17  
- Spring Boot 2.7.x  
- Spring Security (JWT & OAuth2)  
- MongoDB Atlas  
- Apache Kafka  
- Spring Boot Mail (for email notifications)  
- Lombok (for reducing boilerplate code)  
- Maven (build and dependency management)  

---

## Getting Started

### Prerequisites

- Java 17 or higher installed  
- Maven installed  
- Git installed  
- MongoDB Atlas account  
- Redis server (or managed Redis)  
- Optional: Kafka setup if using messaging features  

### Clone the Repository

```bash
git clone https://github.com/USERNAME/JournalEntryApp.git
cd JournalEntryApp
