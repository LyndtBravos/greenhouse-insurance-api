# 🌿 Greenhouse Insurance API

A full-featured **Spring Boot REST API** to calculate life insurance quotes based on client and dependent details, store them in an H2 database, and send email quote summaries — optionally with PDF attachments and CC'ed to dependents.

---

## 📌 Key Features

- 🚀 Dynamic Quote Generation (cover + premium)
- 📩 Email Delivery + PDF Attachment (optional)
- 🕒 Email Retry System for Failures
- 📂 Modular Client + Dependent + Quote Design
- 🧬 Family-Based Quote Engine
- 🔐 Admin-Only Retry Endpoint
- 🗃️ H2 In-Memory DB (for local testing)
- 🧪 Ready for Integration & Unit Testing
- 🖥️ React Frontend Coming Soon

---

## 🧪 API Endpoints

All endpoints are prefixed with `/api/`.

---

### 📬 Email API (`/email`)
| Method | Endpoint                         | Description                                      |
|--------|----------------------------------|--------------------------------------------------|
| GET    | `/get/one/{id}`                  | Get a single email log by ID                     |
| GET    | `/get/all/{id}`                  | Get all email logs for a client ID               |
| GET    | `/get/all`                       | Get all email logs                               |
| POST   | `/create`                        | Save a new email log                             |
| PUT    | `/update`                        | Update existing email log                        |
| DELETE | `/delete/{id}`                   | Delete email log by ID                           |
| POST   | `/resend/failed?adminKey=SECRET` | **Admin-only:** Retry all failed email attempts  |

---

### 👤 Client API (`/client`)
| Method | Endpoint              | Description                        |
|--------|-----------------------|------------------------------------|
| GET    | `/get/one/{id}`       | Get client by ID                   |
| GET    | `/get/all/{id}`       | Get all clients for user ID        |
| GET    | `/get/all`            | Get all clients                    |
| POST   | `/create`             | Create new client                  |
| PUT    | `/update`             | Update client                      |
| DELETE | `/delete/{id}`        | Delete client by ID                |

---

### 👨‍👩‍👧‍👦 Dependent API (`/dependent`)
| Method | Endpoint         | Description                        |
|--------|------------------|------------------------------------|
| GET    | `/{id}`          | Get dependent by ID                |
| GET    | `/user/{id}`     | Get dependents for a client ID     |
| GET    | `/`              | Get all dependents                 |
| POST   | `/create`        | Create a new dependent             |
| PUT    | `/update`        | Update dependent                   |
| DELETE | `/delete/{id}`   | Delete dependent by ID             |

---

### 📦 Plan API (`/plan`)
| Method | Endpoint                    | Description                       |
|--------|-----------------------------|-----------------------------------|
| GET    | `/get/all`                  | Get all plans                     |
| GET    | `/get/plan/{id}`            | Get a specific plan               |
| GET    | `/get/user/{clientID}`      | Get all plans for client ID       |
| POST   | `/create`                   | Create a new plan                 |
| PUT    | `/update`                   | Update an existing plan           |
| DELETE | `/delete/{planID}`          | Delete plan by ID                 |

---

### 👨‍👩 Family API (`/family`)
| Method | Endpoint               | Description                            |
|--------|------------------------|----------------------------------------|
| GET    | `/?email={email}`      | Get entire family profile by email     |

---

### 🧾 Quote API (`/quote`)
| Method | Endpoint                             | Description                                          |
|--------|--------------------------------------|------------------------------------------------------|
| POST   | `/generate`                          | Generate a quote from client details (dynamic calc)  |
| GET    | `/get/one/{id}`                      | Get quote by ID                                      |
| GET    | `/get/all/{id}`                      | Get all quotes for a client ID                       |
| GET    | `/get/all`                           | Get all quotes                                       |
| POST   | `/create`                            | Save a new quote                                     |
| PUT    | `/update`                            | Update existing quote                                |
| DELETE | `/delete/{id}`                       | Delete quote by ID                                   |
| POST   | `/email/{emailSendAttachment}`       | Email quote to client (optional PDF + CC dependents) |

📧 **Email logic auto-generates a PDF attachment** if `emailSendAttachment = true`.

🧠 Family JSON body example:
{
    "user": {
        "id": 1,
        "name": "Brian",
        "surname": "Mthembu",
        "email": "lindtbravos@gmail.com",
        "password": "c0782v8za5u4698464629asy2103dc3c32417bc421"
    },
    "client": {
        "id": 1,
        "userID": 1,
        "age": 35,
        "gender": "MALE",
        "health": "GOOD",
        "location": "Cape Town",
        "smoker": false,
        "creditScore": 710
    },
    "dependentList": [
        {
            "id": 1,
            "name": "Kayla",
            "surname": "Smith",
            "DOB": "2012-05-14",
            "email": "kayla.smith@example.com",
            "relationship": "CHILD",
            "userID": 1,
            "dob": "2012-05-14"
        }
    ]
}

👥 User API (/user)

| Method | Endpoint              | Description                     |
| ------ | --------------------- | ------------------------------- |
| GET    | `/get/all`            | Get all users                   |
| GET    | `/get/user` (body)    | Get user using full user object |
| GET    | `/get/{email}`        | Get user by email               |
| POST   | `/create`             | Create a new user               |
| PUT    | `/update/all/details` | Update full user details        |
| PUT    | `/update/password`    | Update user password            |
| DELETE | `/delete/{userID}`    | Delete user by ID               |


🛠 Run Locally

    Clone this repo

    Open in IntelliJ or preferred IDE

    Run the GreenhouseApiApplication.java class

    Visit endpoints via: http://localhost:8080/api/...

    DB is H2 in-memory – auto-reset on each restart (Declared all on schema.sql - Link (http://localhost:8080/h2-console))


📁 Sample Quote Flow

    User signs up (/user/create)

    Client & dependents added

    Quote generated via /quote/generate

    Quote emailed with /quote/email/{true}



🚀 Roadmap

Quote calculation engine

Email service with retry logic

PDF generator

Frontend in React (coming soon)

Admin dashboard for logs & reports

    Risk score modeling (future AI-based)


👨‍💻 Author

Crafted with 💚 by Brian Mthembu


📜 License

MIT License — feel free to fork & build on it!
