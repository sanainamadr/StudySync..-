# 📚 StudySync — Complete Setup Guide (Beginner Friendly)

---

## 🧰 STEP 1: Install Required Tools

Before anything, install these tools on your laptop:

### 1. Java 17+
- Go to: https://adoptium.net/
- Download **Eclipse Temurin 17 (LTS)**
- Install it, then open a terminal and run:
  ```
  java -version
  ```
  You should see something like `openjdk 17.x.x`

### 2. Maven (Build Tool for Java)
- Go to: https://maven.apache.org/download.cgi
- Download the **Binary zip archive**
- Extract it, add the `bin` folder to your system PATH
- Verify in terminal:
  ```
  mvn -version
  ```

### 3. MySQL (Database)
- Go to: https://dev.mysql.com/downloads/installer/
- Download **MySQL Installer (Windows)** or use Homebrew on Mac:
  ```
  brew install mysql
  ```
- During setup, set a root password (remember it — you'll need it!)
- Verify:
  ```
  mysql -u root -p
  ```

### 4. VS Code
- Go to: https://code.visualstudio.com/
- Install these VS Code extensions:
  - **Extension Pack for Java** (by Microsoft)
  - **Spring Boot Extension Pack** (by VMware)
  - **MySQL** (by cweijan) — for DB viewing inside VS Code

### 5. Node.js (for Tailwind CSS)
- Go to: https://nodejs.org/
- Download **LTS version** and install
- Verify:
  ```
  node -v
  npm -v
  ```

---

## 🗃️ STEP 2: Set Up the Database

1. Open terminal and log into MySQL:
   ```
   mysql -u root -p
   ```
2. Run this command to create the database:
   ```sql
   CREATE DATABASE studysync;
   ```
3. Exit MySQL:
   ```
   exit
   ```

Now open the file `src/main/resources/application.properties` in your project and update:
```
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

---

## 📁 STEP 3: Project Folder Structure

Your complete project looks like this:

```
StudySync/
├── backend/                      ← Spring Boot project
│   ├── src/main/java/com/studysync/
│   │   ├── StudySyncApplication.java
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   ├── TaskController.java
│   │   │   ├── NoteController.java
│   │   │   └── StudySessionController.java
│   │   ├── model/
│   │   │   ├── User.java
│   │   │   ├── Task.java
│   │   │   ├── Note.java
│   │   │   └── StudySession.java
│   │   ├── repository/
│   │   │   ├── UserRepository.java
│   │   │   ├── TaskRepository.java
│   │   │   ├── NoteRepository.java
│   │   │   └── StudySessionRepository.java
│   │   └── service/
│   │       └── UserService.java
│   └── src/main/resources/
│       └── application.properties
│
└── frontend/                     ← HTML/Tailwind/JS pages
    ├── index.html                (Home/Landing page)
    ├── login.html
    ├── signup.html
    ├── dashboard.html
    ├── timer.html
    ├── tasks.html
    ├── notes.html
    ├── profile.html
    └── js/
        ├── auth.js
        ├── timer.js
        ├── tasks.js
        ├── notes.js
        └── dashboard.js
```

---

## 🚀 STEP 4: Run the Backend

1. Open terminal inside the `backend/` folder
2. Run:
   ```
   mvn spring-boot:run
   ```
3. You should see: `Tomcat started on port(s): 8080`
4. Backend is now running at: `http://localhost:8080`

---

## 🌐 STEP 5: Open the Frontend

1. Open the `frontend/` folder in VS Code
2. Install the **Live Server** extension in VS Code
3. Right-click `index.html` → **Open with Live Server**
4. Your app opens in the browser at `http://127.0.0.1:5500`

---

## 🔗 STEP 6: How Frontend Talks to Backend (API)

The frontend uses `fetch()` in JavaScript to call the backend.

Example — Login request:
```javascript
fetch('http://localhost:8080/api/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ email: 'user@email.com', password: '123456' })
})
.then(res => res.json())
.then(data => console.log(data));
```

---

## 📋 API Endpoints Reference

| Feature       | Method | URL                              |
|---------------|--------|----------------------------------|
| Register      | POST   | /api/auth/register               |
| Login         | POST   | /api/auth/login                  |
| Get Tasks     | GET    | /api/tasks/{userId}              |
| Add Task      | POST   | /api/tasks                       |
| Update Task   | PUT    | /api/tasks/{id}                  |
| Delete Task   | DELETE | /api/tasks/{id}                  |
| Get Notes     | GET    | /api/notes/{userId}              |
| Save Note     | POST   | /api/notes                       |
| Delete Note   | DELETE | /api/notes/{id}                  |
| Log Session   | POST   | /api/sessions                    |
| Get Sessions  | GET    | /api/sessions/{userId}           |

---

## ✅ STEP 7: Test Your APIs

Install **Postman**: https://www.postman.com/downloads/

Test registration:
- Method: POST
- URL: `http://localhost:8080/api/auth/register`
- Body (JSON):
  ```json
  {
    "name": "Test User",
    "email": "test@email.com",
    "password": "test123"
  }
  ```

---

## 🎯 Features Checklist

- [x] User Signup / Login / Logout
- [x] Pomodoro Study Timer (25/5 min)
- [x] Task Manager (add, complete, delete)
- [x] Notes Section (save & delete)
- [x] Daily Study Streak tracker
- [x] Dashboard with stats
- [x] Responsive UI (mobile + desktop)
- [x] Session history stored in MySQL

---

## 💡 Tips for Beginners

1. **Run backend first**, then open frontend
2. If you get CORS errors in browser console, the backend already has CORS enabled — make sure backend is running
3. Use browser **DevTools → Console** (F12) to debug JS errors
4. MySQL data persists between runs — you don't lose data when you restart Spring Boot
5. Each user's data is stored separately using their `userId`
