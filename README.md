# ExpenseFlow – Personal Expense Tracker

ExpenseFlow is a full-stack web application designed to help users manage their personal finances efficiently. It allows users to track income and expenses, monitor their financial balance, analyze spending patterns, and generate monthly financial reports through an interactive dashboard.

## 🚀 Features

- **User Authentication:** Secure user signup, login, session management, and logout.
- **Dashboard:** View total balance, income, and expenses.
- **Transaction Management:** Add, view, update, and delete transactions.
- **Financial Analytics:** Visualize income and expenses through interactive charts.
- **Category-wise Analysis:** Monitor spending across different expense categories.
- **Monthly Reports:** View monthly income, expenses, and savings.
- **Search and Filters:** Search transactions and filter them by type and date range.
- **CSV Export:** Download transaction details and monthly financial reports.
- **JSON File Storage:** Store user and transaction data without requiring a database.

## 🛠️ Technologies Used

### Frontend
- React.js
- JavaScript
- HTML5
- CSS3

### Backend
- Java 8
- Spring Boot 2.7.18
- Spring REST APIs
- Maven

### Data Storage
- JSON File Storage
- Jackson

### Development Tools
- Eclipse IDE
- Visual Studio Code
- Git
- GitHub

## 🏗️ Project Architecture

ExpenseFlow follows a client-server architecture:

1. **Frontend:** React.js provides the user interface and dashboard.
2. **Backend:** Spring Boot handles business logic, authentication, and REST API requests.
3. **Storage:** JSON files store user information and expense transactions.
4. **Communication:** The frontend communicates with the backend through REST APIs.

## ⚙️ Installation and Setup

### Prerequisites

Make sure you have installed:

- Java 8
- Node.js and npm
- Maven
- Eclipse IDE or another Java IDE
- Visual Studio Code

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/ExpenseFlow.git
cd ExpenseFlow
```

### 2. Start the Spring Boot Backend

1. Open the backend project in Eclipse.
2. Make sure `application.properties` contains:

```properties
spring.application.name=Springdemo
server.port=8082
```

3. Run `SpringdemoApplication.java` as a Java Application.

Backend URL:

```text
http://localhost:8082
```

### 3. Start the React Frontend

Open a terminal inside the frontend project directory and execute:

```bash
npm install
npm run dev
```

Frontend URL:

```text
http://localhost:5173
```

Open the frontend URL in your browser and create an account to start using ExpenseFlow.

## 🔗 Main API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/signup` | Register a new user |
| POST | `/api/auth/login` | Authenticate a user |
| POST | `/api/auth/logout` | Logout |
| GET | `/api/auth/me` | Check login session |
| GET | `/api/expenses` | Retrieve transactions |
| POST | `/api/expenses` | Add a transaction |
| PUT | `/api/expenses/{id}` | Update a transaction |
| DELETE | `/api/expenses/{id}` | Delete a transaction |
| GET | `/api/expenses/summary` | Retrieve financial summary |

## 📊 Application Modules

- Authentication and User Management
- Expense and Income Management
- Dashboard and Financial Analytics
- Category-wise Spending Analysis
- Monthly Financial Reporting
- Transaction Search and Filtering
- CSV Report Generation

## 🎯 Project Objective

The main objective of ExpenseFlow is to provide a simple and efficient platform for managing personal finances. It helps users understand their spending habits, monitor savings, and make better financial decisions.

## 👨‍💻 Author

**Potnuru Sai Kranthi Kumar**

GitHub: [Your GitHub Profile](https://github.com/kranthi46)
