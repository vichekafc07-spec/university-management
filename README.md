**📌 Overview**

This backend system is designed to handle real-world university operations including:

  . Student Management

  . Academic Management

  . Attendance Tracking

  . Examination & Grading

  . GPA & Ranking System

  . Payment & Invoice Management

  . PDF Reporting

  . Email Notifications

  . JWT Security & RBAC

  . Audit Logging

  . Liquibase Migration

  . Unit & Integration Testing

The project follows a clean layered architecture using DTOs, Mappers, Services, Repositories, and secure REST APIs. Inspired by clean Spring Boot project structures and modular organization practices.

**🚀 Features**

🔐 **Authentication & Security**

  . JWT Authentication
  
  . Refresh Token
  
  . Role & Permission Management
  
  . Dynamic API Permission Authorization
  
  . Token Blacklisting
  
  . Password Encryption
  
  . Spring Security 6


👨‍🎓 **Student Management**

  . Student Registration
  
  . Faculty & Department Assignment
  
  . Student Enrollment
  
  . Academic Status Tracking
  
  . Student Profile Management


👨‍🏫 **Staff & Lecturer Management**

  . Staff Management
  
  . Lecturer Assignment
  
  . Faculty & Department Relations

🏫 **Academic Management**

  . Faculty & Department Management
  
  . Subject & Classroom Management
  
  . Academic Terms
  
  . Timetable Scheduling
  
  . Session Management
  
  . Attendance Tracking
  
📝 **Examination & Grading**

  . Exam Scheduling
  
  . Exam Results
  
  . GPA Calculation
  
  . Semester GPA
  
  . Overall GPA
  
  . Student Ranking
  
  . Dean List
  
  . Transcript PDF Generation

💰 **Payment System**

  . Invoice Management
  
  . Partial Payments
  
  . Payment Tracking
  
  . Receipt PDF Generation
  
  . Email Receipt Attachment
  
  . Revenue Monitoring
  
🔔 **Notification System**

  . In-App Notifications
  
  . Email Notifications
  
  . Automatic Trigger Events

📄 **Reporting**

  . Transcript PDF
  
  . Receipt PDF
  
  . Dashboard Statistics
  
  . Revenue Reports
  
📚 **API Documentation**

  . Swagger / OpenAPI Integration
  
🧪 **Testing**

  . JUnit 5
  
  . Mockito
  
  . H2 Database Testing
  
  . Repository Testing
  
  . Service Layer Testing


🛠️ **Tech Stack**

  **Backend :**
    Java 21
  . Spring Boot 3
  . Spring Security 6
  . Spring Data JPA
  . Hibernate

  **Database :**
    PostgreSQL / MySQL
  . H2 Database (Testing)
  
  **Migration :**
    Liquibase

  **Documentation :**
    Swagger OpenAPI
    
  **Testing :**
    JUnit 5
  . Mockito
  . PDF & Email
  . iText PDF
  . JavaMailSender

📂 **Project Structure**
  
    src/main/java/com/ume/studentsystem
    
    │
    ├── auth
    ├── config
    ├── controller
    ├── dto
    │   ├── request
    │   └── response
    ├── email
    ├── exceptions
    ├── helper
    ├── mapper
    ├── model
    ├── repository
    ├── service
    │   └── impl
    ├── spec
    ├── util
    └── StudentSystemApplication.java

⚙️ **Setup & Installation**

  1️⃣ **Clone Repository**
  
    git clone https://github.com/your-username/university-management-system.git
    cd university-management-system

  2️⃣ **Configure Database**

  Update application.yml
  
    spring:
    datasource:
      url: jdbc:postgresql://localhost:5432/your_database
      username: root
      password: your_password

  3️⃣ **Run Liquibase Migration**
  
  Database tables will be generated automatically on startup.

  4️⃣ **Run Application**

    mvn spring-boot:run

  🧪 **Run Tests**

    mvn test
  
  📘 **Swagger Documentation**

    http://localhost:8080/swagger-ui/index.html

🔒 **Security Features**

  . JWT Authentication
  
  . Refresh Token Rotation
  
  . Dynamic Role & Permission System
  
  . API-Level Authorization
  
  . Secure Password Encryption
  
📈 **System Highlights**

  . Clean Architecture
  
  . DTO + Mapper Pattern
  
  . Soft Delete Support
  
  . Audit Logging
  
  . Transaction Management
  
  . PDF Reporting
  
  . Email Integration
  
  . Financial Validation
  
  . GPA & Ranking System

🧠 **Future Improvements**

  . Docker Deployment
  
  . CI/CD Pipeline
  
  . Redis Cache
  
  . File Storage Service
  
  . WebSocket Notifications
  
  . Microservices Architecture

👨‍💻 **Author**

   SORL VICHIKA
   
     github.com/vichekafc07-spec
     
⭐ **Support**

If you like this project, give it a ⭐ on GitHub.
