# 🚀 Coding Assignment – Design Patterns & Smart Home System

This repository contains **two exercises** implemented in **Java** as part of the placement assignment.  
The focus is on **clean code, SOLID principles, and design patterns** with proper logging, validations, and modular structure.

---

## 📝 Exercise 1 – Design Patterns

### 🎯 Goal
Demonstrate **6 design patterns** (2 Behavioral, 2 Creational, 2 Structural) with simple, real-world inspired use cases.

### ✅ Implemented Patterns
- **Behavioral**
  - Observer → Weather station notifying subscribers  
  - Strategy → Customer support chatbot switching strategies  
- **Creational**
  - Singleton → Application-wide logger  
  - Factory → Database connection factory (MySQL, PostgreSQL, Oracle)  
- **Structural**
  - Adapter → Integrating legacy fan into smart home devices  
  - Decorator → Food ordering system with add-ons (Cheese, Fries, Drink)  

### ▶️ Run Instructions
```bash
# From Exercise_1 root
mkdir out
Get-ChildItem -Recurse -Filter *.java -Path .\src\main\java | ForEach-Object { $_.FullName } > sources.txt
javac -d out (Get-Content sources.txt)
java -cp out com.shivpatel.designpattern.Main 

