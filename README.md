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



