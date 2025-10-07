
# 📘 Online Bookstore – Project Documentation

## 1. Overview
A Command Line Interface (CLI) Online Bookstore Application built with Java, MySQL, and DynamoDB Local, supporting Admin, Customer, and Guest roles.

---

## 2. Architecture
- **UI Layer** – Console UIs (`BookstoreUI`, `AdminUI`)
- **Service Layer** – Business logic (`UserService`, `BookService`, `OrderService`, `CartService`, `RecommendationService`)
- **Persistence Layer** – 
  - MySQL → Users, Books, Orders, Carts
  - DynamoDB → Recommendations
- **Utils** – `ReceiptGenerator`, password hashing, validation

---

## 3. Database Design

### MySQL Tables
- `users` (user_id, username, password, email, role, full_name)
- `books` (book_id, title, author, category, price, isbn, stock_quantity, description)
- `orders` (order_id, user_id, order_date, total_amount)
- `order_items` (item_id, order_id, book_id, quantity, price)
- `cart` (cart_id, user_id, book_id, quantity)

### DynamoDB Table
- `Recommendations` (username [PK], book_title [SK], score)

---

## 4. ⚙️ Technologies Used

| Technology | Where & Why It’s Used |
|------------|-----------------------|
| **Java 17** | Core programming language. Implements the CLI application, services, models, and business logic. |
| **Maven** | Build automation, dependency management, and packaging into an executable JAR. |
| **MySQL** | Relational database for Users, Books, Orders, and Carts (structured transactional data). |
| **DynamoDB (Local / AWS SDK v2)** | NoSQL database for Recommendations, giving fast, scalable personalized suggestions. |
| **Docker & Docker Compose** | Containerized deployment. Spins up MySQL, DynamoDB Local, and the Bookstore app as one stack. |
| **Jenkins** | CI/CD automation on self-hosted setups. Builds, tests, and deploys the app using Docker. |
| **GitHub Actions** | Cloud-based CI/CD. Builds, runs tests, uploads artifacts (JAR), and creates releases when tags are pushed. |
| **JUnit 5** | Unit testing of services like UserService, BookService, CartService, and ReceiptGenerator. |
| **Cucumber (BDD)** | Behavior-driven testing with .feature files (login, search, checkout, recommendations). |
| **BCrypt** | Password hashing to securely store user credentials in MySQL. |
| **OpenPDF** | Generates PDF receipts during checkout, stored in `/receipts`. |
| **AWS CLI** | Used in scripts to create and seed the DynamoDB table locally or on AWS. |
| **Shell Scripts (`scripts/`)** | Helpers for seeding DynamoDB, managing Docker containers, and running automated tests. |

---

## 5. Setup Instructions

### Local Run
```bash
mysql -u root -p < src/main/resources/schema.sql
mysql -u root -p < src/main/resources/data.sql
mvn clean package
java -jar target/online-bookstore.jar
```

### Docker Run
```bash
docker-compose up --build -d
docker logs -f bookstore-app
```

---

## 6. CI/CD
- Jenkinsfile → Maven build, test, package, deploy with Docker Compose
- GitHub Actions → Workflow runs tests, builds, uploads JAR, and creates releases

---

## 7. Testing
- **JUnit** → Unit tests for services
- **Cucumber BDD** → Feature files validating core flows

Run:
```bash
mvn test
mvn verify -Pcucumber
```

---

## 8. Sample Admin Credentials
```
username: admin
password: admin123
```

---

## 9. Future Enhancements
- REST API for frontend
- Email notifications for receipts
- Payment gateway simulation
- More advanced recommendation engine
