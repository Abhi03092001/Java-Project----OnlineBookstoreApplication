

---

## 🧩 Merged "Super" Project Notes

This project merges:
- Our previous Admin, receipts, Docker, CI/CD, and JUnit tests
- The uploaded project's DynamoDB-oriented pieces, sessions, scripts, and BDD tests

### Run Unit Tests
```bash
mvn test
```

### Run Cucumber BDD Tests (optional)
```bash
mvn -Pcucumber test
```

### DynamoDB Local
Use `scripts/setup-dynamodb-local.sh` or:
```bash
docker-compose up -d dynamodb
```



## Day 6 – DynamoDB Integration

**Goal:** Display top 5 recommended books per user from DynamoDB.

### 1) Install & Start DynamoDB Local
```bash
docker-compose up -d dynamodb
```

### 2) Create table + seed sample data (requires AWS CLI)
```bash
export DYNAMODB_ENDPOINT=http://localhost:8000
./scripts/create-dynamodb-table.sh
```

### 3) Run the app
```bash
mvn clean package
java -jar target/online-bookstore.jar
```

### 4) Demo in UI
- Sign in as `testuser` (create via Sign Up if needed)
- Go to **Recommendations** → shows top 5 from DynamoDB.


### Automatic DynamoDB Seeding on User Registration
When a new user signs up, the application now automatically seeds 2–3 default recommendations into the local/remote DynamoDB `Recommendations` table:
- Clean Code (0.85)
- Effective Java (0.80)
- The Pragmatic Programmer (0.78)

If DynamoDB Local is running (via `docker-compose up -d dynamodb` or `DYNAMODB_ENDPOINT=http://localhost:8000`), the seeding occurs immediately after registration.


### Dynamic Recommendations (Checkout + Browsing)
- After **checkout**, related books are pushed into DynamoDB as new recommendations (e.g. buy *Clean Code* → recommend *Refactoring*).  
- After **browsing a book detail**, a category-based recommendation is added (e.g. view *Kubernetes in Action* → recommend "More in Cloud/DevOps").  
- These recommendations appear instantly in the **Customer Menu > Recommendations**.
