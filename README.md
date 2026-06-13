## 🏠 FlatHub — Flat Management System

A web-based flat management system built in **Java (MVC)** with a **MySQL** database, **Spark Java REST API**, and an **HTML/CSS/JS** frontend dashboard.

---
 📋 Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Setup & Installation](#setup--installation)
- [Running the Project](#running-the-project)
- [API Endpoints](#api-endpoints)
- [Features](#features)
- [Database Schema](#database-schema)
- [Known Issues & Fixes](#known-issues--fixes)

---
 Project Overview

FlatHub allows a landlord (admin) to manage their residential flats from a browser dashboard. It handles tenants, rent receipts, vacant flats, and data backups — all connected through a REST API that bridges the Java backend to the frontend.

Data is stored in two places:
- MySQL— primary storage (tenants, receipts, vacancies, landlord auth)
- Text files — secondary backup (`TENANT.txt`, `RECIEPT.txt`, `Vacant.txt`)

---
## Tech Stack

| Layer | Technology | Purpose |
|---|---|---|
| Backend | Java (OOP) | Business logic, MVC models |
| Web Server | Spark Java | REST API, HTTP routing |
| JSON | Gson (Google) | Java ↔ JSON serialization |
| Database | MySQL 8.0 | Primary persistent storage |
| DB Driver | JDBC | Java to MySQL connection |
| Backup | Text Files | Offline secondary backup |
| Frontend | HTML / CSS / JS | Browser dashboard UI |
| Auth | Custom Java class | Single admin login session |

---

## Project Structure

```
FlatHub/
│
├── backend/
│   ├── Controller/
│   │   ├── API.java              # Spark REST API server (main entry point)
│   │   ├── authorization.java    # Login/logout session management
│   │   └── File_handler.java     # Text file backup operations
│   │
│   ├── Model/
│   │   ├── Tenant.java           # Tenant data model
│   │   └── RECIEPT.java          # Receipt data model
│   │
│   └── Database/
│       └── Database.java         # MySQL JDBC operations + in-memory lists
│
├── src/Data/
│   ├── TENANT.txt                # Auto-generated tenant backup
│   ├── RECIEPT.txt               # Auto-generated receipt backup
│   └── Vacant.txt                # Auto-generated vacancy backup
│
├── frontend/
│   └── index.html                # Full dashboard UI (login + all pages)
│
├── schema.sql                    # MySQL database schema
└── README.md
```

---

## Setup & Installation

### 1. Prerequisites

- Java JDK 8 or higher
- MySQL 8.0
- IntelliJ IDEA (recommended)
- A browser (Chrome/Firefox)

### 2. Add External JAR Libraries

In IntelliJ: `File → Project Structure → Libraries → + → Java`

Add these two JARs:
- `spark-core-2.9.4-jar-with-dependencies.jar`
- `gson-2.x.jar`

Download from:
- Spark: https://mvnrepository.com/artifact/com.sparkjava/spark-core
- Gson: https://mvnrepository.com/artifact/com.google.code.gson/gson

### 3. Set Up MySQL Database

```sql
-- Run schema.sql in MySQL Workbench or terminal
mysql -u root -p < schema.sql
```

### 4. Configure Database Connection

Open `Database/Database.java` and update your credentials:

```java
private static final String URL  = "jdbc:mysql://localhost:3306/flathub";
private static final String USER = "root";
private static final String PASS = "your_password_here";
```

### 5. Create Data Directory

Make sure this folder exists (for file backups):

```
src/Data/
```

---

## Running the Project

### Step 1 — Start the Backend

Run `API.java` (the `main` method is inside `Controller/API.java`).

You should see:

```
API Server started on http://localhost:4567
  GET  /api/tenants
  POST /api/tenants
  ...
```

### Step 2 — Open the Frontend

Open `frontend/index.html` in your browser directly (no server needed).

### Step 3 — Login

```
Username: admin
Password: admin
```

> The frontend works standalone with demo data even without the backend running (localStorage fallback). When backend is running, all data comes from MySQL via the API.

---

## API Endpoints

Base URL: `http://localhost:4567`

## Tenants

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/tenants` | Get all tenants |
| POST | `/api/tenants` | Add a new tenant (JSON body) |
| DELETE | `/api/tenants/:flat` | Remove tenant by flat number |
| GET | `/api/tenants/search/cnic?cnic=` | Search by CNIC |
| GET | `/api/tenants/search/flat?flat=` | Search by flat number |
| GET | `/api/tenants/search/name?name=` | Search by name |

### Receipts

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/receipts` | Get all receipts |
| POST | `/api/receipts?flat_no=A1` | Generate receipt for a flat |

### Vacancies

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/vacancies` | Get all vacant flats |

### Stats & Backup

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/stats` | Dashboard stats (tenants, revenue, etc.) |
| GET | `/api/backup` | Get all data as JSON |
| POST | `/api/backup` | Trigger file backup |

### Example — Add Tenant (POST /api/tenants)

```json
{
  "name": "Ali Hassan",
  "cnic": "35201-1234567-1",
  "phone_no": "0300-1234567",
  "flat_no": "A1",
  "rent": 15000,
  "dues": 0,
  "type": "Monthly",
  "joinDate": "2024-01-15"
}
```

---

## Features

### ✅ Tenant Management
- Add tenants with full details: name, CNIC, phone, flat number, rent, dues, payment type, join date
- Remove tenants — automatically marks their flat as vacant and keeps a record
- View all active tenants in a searchable table

### ✅ Receipt Generation
- Generate monthly or yearly rent receipts per tenant
- Duplicate prevention — same tenant cannot get two receipts for the same period

### ✅ Smart Search
- Search tenants by CNIC, flat number, or name

### ✅ Vacant Flat Tracking
- Automatically tracks vacancies when tenants are removed
- Shows previous tenant name, CNIC, and vacated date

### ✅ Dashboard Stats
- Live counts: active tenants, vacant flats, total receipts, monthly revenue

### ✅ Data Backup
- Saves tenant, receipt, and vacancy records to text files
- Triggered automatically on every add/remove, or manually from the dashboard

### ✅ Auth & Security
- Login-protected dashboard (username + password)
- Session managed by `authorization.java`

---

## Database Schema

### Tables

**tenants**
```
id, name, cnic, phone_no, flat_no, rent, dues, type, joinDate, active
```

**receipts**
```
id, recieptNo, tenantName, flatNo, amount, paymentType, date
```

**vacant_flats**
```
id, flat_no, previous_tenant, cnic, vacated_date
```

**landlord**
```
username, password
```

---

## Known Issues & Fixes

| Severity | Issue | Fix |
|---|---|---|
| HIGH | Missing `public` on Database methods | Add `public` to `add_tenant()`, `remove_tenant()`, `pay_rent()` |
| HIGH | `auto_backup()` / `backup_all()` missing in File_handler | Add as wrapper methods calling the three backup methods |
| MEDIUM | External JARs not on classpath | Add Spark and Gson JARs via IntelliJ Project Structure |
| LOW | `File_handler implements Serializable` (unnecessary) | Remove it — class uses FileWriter not ObjectOutputStream |
| LOW | FileWriter overwrites backup on every call | Use `new FileWriter(path, true)` if you want history |

---

## MVC Architecture

```
VIEW (Frontend)          CONTROLLER (Java)         MODEL (Data)
─────────────────        ──────────────────        ─────────────────
index.html               API.java (Spark)          Tenant.java
Login Page          ──►  authorization.java   ──►  RECIEPT.java
Dashboard                File_handler.java         Database.java
Tenant Manager           REST Endpoints            MySQL (primary)
Receipt Generator        Session/Auth checks       Text files (backup)
Search & Vacancies       Backup triggers           JDBC Connection
localStorage fallback
```

---

## Author

Built as an academic Java project demonstrating MVC architecture, REST API design, relational database integration, and a fully connected browser frontend.
