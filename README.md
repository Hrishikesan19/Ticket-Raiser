# 🛠️ Departmental Ticket Raiser System – Spring Boot Project

A full-stack enterprise-grade Helpdesk Ticketing System built using **Spring Boot**, designed with **department-based routing**, **role-based access**, and support for **personal and shared tickets**.

---

## 📌 Table of Contents

- [Project Overview](#project-overview)
- [System Features](#system-features)
- [User Roles](#user-roles)
- [Ticket Flow](#ticket-flow)
- [Access Control](#access-control)
- [Shared vs Personal Tickets](#shared-vs-personal-tickets)
- [System Architecture](#system-architecture)
- [Database Schema](#database-schema)
- [Entity Relationships](#entity-relationships)
- [Tech Stack](#tech-stack)
- [Next Steps](#next-steps)

---

## 📖 Project Overview

This is a web-based helpdesk system where employees can raise tickets **against other departments** within a company. These tickets are monitored by an **Admin**, routed to the **correct department**, and resolved by **department agents**.

Supports:
- Personal and shared tickets
- Full ticket lifecycle (Open → In Progress → Resolved)
- Role-based access (Employee, Agent, Admin)

---

## ✨ System Features

- 🔐 JWT Authentication & Role-based authorization
- 🏢 Department-based ticket routing
- 👥 Shared and private tickets
- 🗂️ Admin dashboard with all ticket insights
- 📬 Comments and response thread per ticket
- 🕒 Ticket timestamps and status tracking

---

## 👤 User Roles

| Role     | Capabilities |
|----------|--------------|
| **Employee** | Raise tickets, view personal tickets, see responses |
| **Agent**    | View tickets for their department, assign and resolve |
| **Admin**    | Full visibility and control over all tickets |

---

## 🔁 Ticket Flow

1. **Employee A (from HR)** raises a ticket targeting **Finance department**
2. The ticket is stored in **Admin DB** and **forwarded to Finance**
3. **Finance agent** handles the ticket and updates status
4. The **response is shown to User A**
5. If it's a **shared ticket**, all of HR can view it

---

## 🔒 Access Control Summary

| User Type         | Ticket Type | Can View |
|------------------|-------------|----------|
| Creator           | Personal    | ✅ Yes   |
| Creator's Dept    | Shared      | ✅ Yes   |
| Creator's Dept    | Personal    | ❌ No    |
| Target Dept Agent | All         | ✅ Yes   |
| Admin             | All         | ✅ Yes   |

---

## 👥 Shared vs Personal Tickets

### Shared Ticket
- Visible to **all members** of the creator's department
- Ideal for department-wide issues

### Personal Ticket
- Visible only to the creator and resolver
- Default for most support queries

---

## 🧱 System Architecture


Frontend (React / Thymeleaf)
|
\[API Gateway - Spring Cloud]
|
┌────────┬────────┬────────────┐
\| Auth   | Admin  | TicketRouter
└────┬───┴────┬───┴────────────┘
↓        ↓
Department Services (IT, HR, Finance)


- **AuthService:** Login, JWT, registration
- **AdminService:** Stores all tickets globally
- **TicketRouterService:** Forwards ticket to appropriate department
- **DepartmentServices:** Handle tickets for respective departments

---

## 🧩 Database Schema
---

### 📍 `departments`

| Column   | Type     | Description                      |
|----------|----------|----------------------------------|
| id       | INT (PK) | Primary Key                      |
| name     | VARCHAR  | Department name (e.g., HR, IT)   |
| active   | BOOLEAN  | Used to soft-delete departments  |
| created_at | TIMESTAMP | Optional audit info          |
| updated_at | TIMESTAMP | Optional audit info          |

---

### 📍 `users`

| Column      | Type     | Description                        |
|-------------|----------|------------------------------------|
| id          | BIGINT   | Primary Key                        |
| name        | VARCHAR  | Full name                          |
| email       | VARCHAR  | Unique login identifier            |
| password    | VARCHAR  | Hashed password                    |
| role        | ENUM     | EMPLOYEE, AGENT, ADMIN             |
| department_id | INT    | FK → departments(id)               |
| enabled     | BOOLEAN  | Optional: active/inactive user     |

---

### 📍 `tickets`

| Column         | Type     | Description                                  |
|----------------|----------|----------------------------------------------|
| id             | BIGINT   | Primary Key                                  |
| title          | VARCHAR  | Ticket subject                               |
| description    | TEXT     | Detailed issue description                   |
| created_by_id  | BIGINT   | FK → users(id)                               |
| from_department_id | INT  | FK → departments(id)                         |
| to_department_id   | INT  | FK → departments(id)                         |
| assigned_to_id | BIGINT   | FK → users(id), nullable                     |
| status         | ENUM     | OPEN, IN_PROGRESS, RESOLVED, CLOSED          |
| shared         | BOOLEAN  | True if visible to full origin department    |
| created_at     | TIMESTAMP| Timestamp of creation                        |
| updated_at     | TIMESTAMP| Timestamp of last update                     |

---

### 📍 `comments`

| Column     | Type     | Description                                 |
|------------|----------|---------------------------------------------|
| id         | BIGINT   | Primary Key                                 |
| ticket_id  | BIGINT   | FK → tickets(id)                            |
| user_id    | BIGINT   | FK → users(id)                              |
| message    | TEXT     | Comment content                             |
| internal   | BOOLEAN  | True = internal (agent/admin-only)          |
| created_at | TIMESTAMP| Timestamp of the comment                    |

---

## 🔗 Entity Relationships

users
└─< tickets (created\_by)
└─< comments (ticket\_id, user\_id)

users
└─< tickets (assigned\_to)



--------------------------------------------------------------------------------

## ⚙️ Tech Stack
_____________________________________
| Layer       | Technology           |
|-------------|----------------------|
| Backend     | Spring Boot (REST)   |
| Frontend    | React / Thymeleaf    |
| Security    | Spring Security + JWT |
| API Gateway | Spring Cloud Gateway |
| DB          | MySQL / PostgreSQL   |
| Discovery   | Eureka (optional)    |
| Messaging   | Kafka / RabbitMQ (optional) |

---

## ✅ Next Steps

- [ ] Define all `@Entity` classes in Spring Boot
- [ ] Implement `User`, `Ticket`, and `Comment` services
- [ ] Build frontend forms and dashboards
- [ ] Deploy using Docker or local Postgres
- [ ] Add enhancements like file uploads, email notifications, or analytics

---

## 💬 Want to Contribute?

This project is great for learning:
- Microservices
- Role-based access control
- Real-world system design in Spring Boot

