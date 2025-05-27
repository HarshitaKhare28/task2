# Task 2: Hibernate CRUD Operations with Oracle Database

## Overview

This task demonstrates basic CRUD (Create, Read, Update, Delete) operations on a store inventory using Hibernate ORM with an Oracle Database.  
It is a Java console application that allows adding, fetching, updating, and deleting items stored in the Oracle DB.

## Technologies Used

- **Java** (JDK 8+)
- **Hibernate ORM** (v5.x)
- **Oracle Database** (XE or other edition)
- **Oracle JDBC Driver** (`ojdbc8.jar`)
- **Other JAR dependencies**: Hibernate core, commons-logging, etc.

## Repository Structure
```
task2/
│
├── src/store/ # Java source files (Main.java, Item.java, HibernateUtil.java)
├── lib/ # External JAR libraries (Hibernate, Oracle JDBC driver, etc.)
├── hibernate.cfg.xml # Hibernate configuration with Oracle DB connection details
├── out/ # Compiled Java classes
└── README.md # This documentation file
```
## Prerequisites

- Oracle Database installed and running
- Oracle user with privileges to create tables (e.g. `storeuser`)
- JDK installed
- All required JAR files (Hibernate and Oracle JDBC driver) added to `lib/` folder

## Setup Instructions

1. **Configure Oracle Database:**

   - Create the user/schema:
     ```sql
     CREATE USER storeuser IDENTIFIED BY store;
     GRANT CONNECT, RESOURCE TO storeuser;
     ```
   
   - Create `items` table or let Hibernate auto-create it by setting `hbm2ddl.auto` to `update`.

2. **Configure Hibernate:**

   - Edit `hibernate.cfg.xml` to reflect your Oracle DB connection credentials and URL. Example:
     ```xml
     <property name="hibernate.connection.url">jdbc:oracle:thin:@localhost:1521/xe</property>
     <property name="hibernate.connection.username">storeuser</property>
     <property name="hibernate.connection.password">store</property>
     ```

3. **Compile the Project:**

   ```bash
   javac -cp "lib/*" src/store/*.java -d out
   ```
4. **Run the Application:**
   ```bash
   java -cp "out;lib/*" store.Main
   ```
   
