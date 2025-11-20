# 🚘 VCRTS – Vehicular Cloud Real-Time System  
[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)]()
[![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)]()

### *A cloud-enhanced computing platform leveraging unused vehicular processing power to execute distributed tasks in real-time.*

---

## 📖 Overview
VCRTS (Vehicular Cloud Real-Time System) enables **Vehicle Owners** to contribute unused computational resources into a shared pool, while **Job Owners** submit tasks requiring execution.  
A **Vehicular Cloud Controller** manages scheduling and resource allocation using a **FIFO (First-In, First-Out)** algorithm.

Built as a Software Engineering capstone project simulating future smart-city computing infrastructures.

---

## 👥 Team Members
- **Aidin Dzaferovic**
- **Christopher Singh**
- **Brandon Whitley**
- **Xiaoyao Wu**
- **Shivpreet Singh**

---

## 🎯 Core Objectives
- Enable registration and utilization of vehicle compute power
- Support job submissions with dynamic progress monitoring
- Ensure fair allocation with FIFO scheduling
- Provide real-time status updates for all operational tasks
- Build an intuitive GUI for users and admin controller workflows

---

## 🧑‍💼 User Roles
| Role | Responsibilities |
|------|------------------|
| **Job Owner** | Submit computational jobs; track pending/in-progress/completed states |
| **Vehicle Owner** | Register vehicles with performance details; manage availability |
| **Vehicular Cloud Controller** | Accept/decline submissions, manage scheduling and resource allocation |

All access requires login authentication.

---

## 🚀 Key Features
- 🔐 **Account Login System**
- 🚗 **Vehicle Pool Management**
- 🧾 **Job Submission + Monitoring**
- 🔄 **FIFO Scheduling Algorithm**
- 📊 **Real-time Status Tracking**
- 🔍 **Validation & Error Handling**
- 💽 **MySQL Persistent Storage**
- 🖥️ **Modernized Java Swing GUI**
- ✔ **Controller popups** for accept/decline actions

---

## 🛠️ Tech Stack
| Component | Technology |
|----------|------------|
| Programming Language | Java (JDK 17+) |
| UI Framework | Java Swing |
| Database | MySQL Server |

---

## 📈 System Requirements
- Windows 10/11 or macOS
- Java 17+ installed
- MySQL Server (local instance)

---

## ⚙️ Allocation Workflow
1. Job Owner submits a task
2. Controller approves request → added to queue
3. Vehicle registered → added to compute pool
4. FIFO logic assigns next job to available vehicle
5. Status updates until job is completed

Efficient, fair, and predictable execution.

---
