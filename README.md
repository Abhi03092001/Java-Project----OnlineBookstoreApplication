
# Online Bookstore (CLI, Java)

A complete command-line bookstore with MySQL persistence, DynamoDB-backed recommendations, browsing history (LinkedList), cart & checkout, and BDD tests (Cucumber).

## Prerequisites
- Java 11+
- Maven 3.8+
- MySQL 8+
- (Optional) DynamoDB Local (for recommendations demo)

## 1) MySQL Setup
```sql
-- In MySQL shell:
SOURCE src/main/resources/schema.sql;
-- Or copy/paste its contents
```
Update DB credentials in: `src/main/java/com/bookstore/database/DatabaseConnection.java`
```java
private static final String URL = "jdbc:mysql://localhost:3306/bookstore_db";
private static final String USER = "root";
private static final String PASSWORD = "your_password"; // change this
```

## 2) DynamoDB Local (optional for demo)
Option A: Docker
```bash
docker run -p 8000:8000 amazon/dynamodb-local
```
Option B: Download JAR
- https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html
- Run: `java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb -inMemory -port 8000`

The app will auto-create the `BookRecommendations` table.

## 3) Build & Run
```bash
mvn clean package
java -jar target/online-bookstore-1.0-SNAPSHOT.jar
```
If you see DB errors, recheck MySQL is running and credentials are correct.

## 4) Run BDD tests
```bash
mvn -q -Dtest=TestRunner test
```
HTML report: `target/cucumber-reports.html`

## Sample Flow
1. Launch → Enter Bookstore
2. Sign up / Sign in / Guest /Admin
3. Search or Browse books → View details → Add to cart
4. Checkout (non-guest) → Order stored → Recommendations generated
5. View Browsing History / Recommendations

## Notes
- Passwords are hashed with BCrypt.
- Recommendations fallback to popular books if DynamoDB is unavailable.
- Adjust console UI and pagination as you like.


---

# 🔄 CI/CD with Jenkins

We provide a `Jenkinsfile` that automates the pipeline:

1. **Checkout** code from GitHub.
2. **Build** with Maven.
3. **Run Tests** (JUnit + Cucumber).
4. **Package** JAR file.
5. **Deploy** JAR to Linux VM over SSH.

### Requirements
- Jenkins with Maven + JDK configured in Global Tools.
- Linux VM with Java + MySQL installed.
- SSH key exchange setup between Jenkins and VM.

### Deployment Steps
- The pipeline copies the JAR to `/opt/bookstore` on the VM.
- Old process is killed, new one is started in background.
- Logs can be found at: `/opt/bookstore/bookstore.log`

### Configuration
All app settings are in `src/main/resources/application.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/bookstore_db
db.user=root
db.password=your_password

dynamodb.endpoint=http://localhost:8000
dynamodb.region=us-east-1
```


---

# 🐳 Run with Docker Compose

We provide a `docker-compose.yml` that launches the entire stack:
- **MySQL 8.0** (with schema auto-loaded)
- **DynamoDB Local**
- **Bookstore App** (Java JAR)

### Steps
```bash
# Build & start everything
docker-compose up --build

# Stop everything
docker-compose down
```

### Services
- Bookstore App → `localhost:8081`
- MySQL → `localhost:3306` (user: bookstore / pass: bookstore123)
- DynamoDB Local → `localhost:8000
