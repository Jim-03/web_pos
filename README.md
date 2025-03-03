# WEB POS
## Table Of Contents
1. [Introduction](#1-introduction)
2. [Features](#2-features)
3. [Technologies](#3-technologies)
   - [Backend](#31-backend)
   - [Frontend](#32-frontend)
   - [Database](#33-backend)
4. [Requirements](#4-requirements)
5. [Installation](#5-installation)

# 1. Introduction
This is a basic web based point of sale system built using Java as the backend language

# 2. Features
- Adding new items to the database
- Viewing the stored items
- Selling the items
- Viewing sales history

# 3. Technologies
## 3.1. Backend
- Java 21 as the backend language
- Spring boot framework
## 3.2. Frontend
- HTML and CSS for the UI
- Vanilla JavaScript for frontend interactivity
## 3.3. Backend
- MySQL database

# 4. Requirements
For this software to work, you'll need the following installed:
- MySQL server
- Java SDK 21
- Maven build tool

# 5. Installation
1. Clone the GitHub repository
```commandline
git clone https://github.com/Jim-03/web_pos.git
cd web_pos
```

2. Go to the location `src/main/resources/application.properties` and update the credentials to match your database credentials
For example: 
```commandline
spring.application.name=Point-Of-Sale
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password
spring.jpa.hibernate.ddl-auto=create
spring.datasource.hikari.maximum-pool-size=20
```
> NOTE:
>   - Ensure the provided database already exists
>   - If you want the tables to be created on every restart, leave spring.jpa.hibernate.ddl-auto=create as it is
>   - Otherwise, change its value to update
3. At the project's root folder, install the project's dependencies. 
```commandline
mvn clean install
```
> NOTE: Requires an internet connection
4. Start the app.
```commandline
mvn spring-boot:run
```
5. Visit the link `http://localhost:8080/` on your browser