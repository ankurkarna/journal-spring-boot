#  Journal App

A full-stack journal entry web application with secure authentication, sentiment analysis, and weather integration. Built with **React (Vite + TypeScript)** on the frontend and **Spring Boot + Java + Redis** on the backend, this project demonstrates modern web development practices, scalable architecture, and production-ready deployment.

---

##  Live Demo

 [Click here to try the app](https://crynza-journal-application.onrender.com/)  
<sub>Hosted on Render (Frontend + Backend)</sub>

---

##  Preview

![image](https://github.com/user-attachments/assets/d83ae2fd-1016-4b69-a575-57cda1ae2b67)

![image](https://github.com/user-attachments/assets/d689ecb3-ff70-4eb4-b592-7df5d3bf8627)

![image](https://github.com/user-attachments/assets/9a999b1a-b409-4607-9be3-7e5910c163b8)

![image](https://github.com/user-attachments/assets/0742ea77-cdbd-4bb1-8da5-0e05d0922c99)

---

##  Features

-  **Journal Entries** – Create, edit, and delete your personal notes.
-  **Authentication** – Secure login & signup with JWT.
-  **Sentiment Analysis** – Auto-detects sentiment in journal entries.
-  **Weather Info** – Pulls current weather data for context.
-  **REST API** – Fully documented with Swagger / OpenAPI.
-  **Vite + React** – Fast, modern frontend setup.
-  **Redis** – Used for caching and pub/sub messaging.
-  **Scheduled Tasks** – Background tasks (e.g. cleanup, notifications).

---

##  Tech Stack

### Frontend
- [React](https://reactjs.org/)
- [Vite](https://vitejs.dev/)
- [TypeScript](https://www.typescriptlang.org/)
- [Tailwind CSS](https://tailwindcss.com/)
- Axios

### Backend
- [Java 17+](https://openjdk.org/projects/jdk/17/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [MongoDB Atlas](https://cloud.mongodb.com/v2/)
- [Redis](https://redis.io/)
- JWT (JSON Web Tokens)
- OpenAPI / Swagger for API Docs
- Maven

### Tools & Deployment
- Git & GitHub
- Render (for deployment)
- Vite Dev Server
- Postman for testing APIs

---

##  Project Structure

```

 journal-app/
├── frontend/
│   └── src/
│       ├── pages/
│       ├── components/
│       ├── services/
│       └── App.tsx, main.tsx, axiosConfig.ts
├── backend/
│   └── src/main/java/com/karna/ankur/journal/
│       ├── controller/
│       ├── service/
│       ├── entity/
│       ├── repository/
│       ├── config/
│       └── utils, scheduler, security, api/

````

---

##  Getting Started Locally

### Prerequisites
- Node.js + npm
- Java 17+
- Maven
- Redis (locally or cloud)
- Mongo (locally or cloud)

---

### 1. Clone the repo

```bash
git clone https://github.com/your-username/journal-app.git
cd journal-app
````

---

### 2. Run the Frontend

```bash
cd frontend
npm install
npm run dev
```

---

### 3. Run the Backend

Make sure Redis is running, then:

```bash
cd backend
./mvnw spring-boot:run
```

Set up environment configs in `application-dev.yml`.

---

##  API Documentation

Once backend is running, access the API docs at:
`http://localhost:8080/swagger-ui/index.html`
