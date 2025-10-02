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
```
---
## 🏠 Exercise 2 – Smart Home System ( Console Application )

A **console-based Smart Home simulation** built in **Java**.  
It allows users to control smart devices (Lights, Thermostat, Door Lock) via a central hub.  
The project demonstrates **Creational, Behavioral, and Structural design patterns** along with **SOLID principles** and **clean code practices**.

---

## 🎯 Features

1. **Device Management**
   - Turn devices ON/OFF  
   - Lock/Unlock door locks  
   - Set thermostat temperature  

2. **Schedules**
   - Add schedules for devices at specific times  
   - Run all scheduled tasks  

3. **Automation with Triggers (Observer)**
   - Example: *If temperature > 75, turn off light*  
   - Supports conditions, thresholds, and linked actions  

4. **Centralized Logging**
   - All actions logged via a **Singleton logger**  

5. **Error Handling**
   - Invalid device IDs  
   - Invalid commands or missing parameters  
   - Unsupported triggers/operators  

---

## 🛠️ Design Patterns Used

- **Factory Method** → `DeviceFactory` creates devices (Light, Thermostat, DoorLock).  
- **Proxy** → `DeviceProxy` controls access to devices before execution.  
- **Observer** → `Trigger` observes device state changes and performs automation.  
- **Singleton** → `AppLogger` provides consistent logging.  

---
### ▶️ Run Instructions
```bash
# From Exercise_2 root
mkdir out
Get-ChildItem -Recurse -Filter *.java -Path .\src\main\java | ForEach-Object { $_.FullName } > sources.txt
javac -d out (Get-Content sources.txt)
java -cp out com.shivpatel.smarthome.SmartHomeMain
```

## 📋 Available Commands

| Command                                | Example                          | Description                                   |
|----------------------------------------|----------------------------------|-----------------------------------------------|
| `list`                                 | `list`                           | Show all devices and their current status      |
| `turnOn <id>`                          | `turnOn 1`                       | Turn ON a device (Light, Thermostat, DoorLock) |
| `turnOff <id>`                         | `turnOff 3`                      | Turn OFF a device / Lock a door               |
| `setTemp <id> <value>`                 | `setTemp 2 75`                   | Set thermostat temperature                    |
| `setSchedule <id> <HH:mm> <command>`   | `setSchedule 1 06:00 turnOn 1`   | Schedule a device command for later execution |
| `run-schedules`                        | `run-schedules`                  | Run all scheduled tasks (simulation)          |
| `addTrigger <param> <op> <thr> <act>`  | `addTrigger temperature > 75 turnOff 1` | Add automation trigger                        |
| `evaluate-triggers`                    | `evaluate-triggers`              | Evaluate triggers against current device state|
| `exit`                                 | `exit`                           | Exit the system                               |



