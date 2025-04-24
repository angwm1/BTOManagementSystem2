# BTO Management System

A command‐line Java application for managing Build-To-Order (BTO) flat applications, officer registrations, and project lifecycle for HDB staff and applicants.

This system was built for the SC2002 Object‐Oriented Design & Programming course at Nanyang Technological University (Semester 2, 2024/2025).

---

## Table of Contents

1. [Features](#features)  
2. [Prerequisites](#prerequisites)  
3. [Setup & Build](#setup)  
4. [Running the Application](#running-the-application)  
5. [Javadoc](#javadoc)  

---

## Features

- **Multi‐role login**: Applicants, HDB Officers, and Managers each have tailored menus and workflows.  
- **Applicant capabilities**: View/apply/withdraw from projects, manage enquiries, view application status, and book a flat when successful.  
- **Officer capabilities**: Everything an applicant can do, plus register to handle projects, view/respond to project enquiries, and process bookings.  
- **Manager capabilities**: Create/edit/delete BTO projects, toggle visibility, approve/reject applications & withdrawals, manage officer registrations, reply to enquiries, and generate summary reports.  
- **Project filtering**: Applicants can filter by neighborhood or flat type; eligibility constraints enforced (age, marital status).  
- **Persistence via Excel**: Loads initial users and projects from `.xlsx` files using Apache POI—no database required.  
- **Clean CLI**: Interactive menus based on role, all in a terminal-based interface.

---

## Prerequisites

- **Java SE 8+** (JDK)  
- **Maven 3+**  
- A terminal (Windows CMD/PowerShell, macOS Terminal, Linux shell)

---

## Setup

1. Clean with maven
2. Do maven install

---

## Javadoc

- Found under target directory
