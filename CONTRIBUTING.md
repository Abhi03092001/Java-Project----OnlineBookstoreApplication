# Contributing to Online Bookstore

Thank you for your interest in contributing to the Online Bookstore project! 🎉

We welcome contributions of all kinds — bug fixes, new features, documentation improvements, and tests.

## 🚀 Getting Started

1. **Fork the repository**  
   Click on the *Fork* button at the top right of the repo.

2. **Clone your fork**
   ```bash
   git clone https://github.com/YOUR-USERNAME/online-bookstore.git
   cd online-bookstore
   ```

3. **Create a new branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

4. **Build the project**
   ```bash
   mvn clean package
   ```

5. **Run tests**
   ```bash
   mvn test
   ```

6. **Start the application**
   ```bash
   java -jar target/online-bookstore.jar
   ```

   Or use Docker Compose:
   ```bash
   docker-compose up --build
   ```

## 🧪 Tests

- Unit tests are in `src/test/java/com/bookstore/`.
- To run all tests:
  ```bash
  mvn test
  ```
- See `TestDemo.txt` for an example test run.

## 📖 Coding Standards

- Use **Java 11+** and follow standard **Java conventions**.
- Use meaningful variable and method names.
- Add Javadoc comments for public classes and methods.
- Format code with 4 spaces indentation.

## 📝 Pull Requests

1. Commit your changes:
   ```bash
   git commit -m "Add: short description of your change"
   ```

2. Push to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```

3. Open a **Pull Request** in the upstream repository.

## ✅ Contribution Checklist

- [ ] Code compiles with no errors (`mvn clean package` passes)
- [ ] All tests pass (`mvn test` passes)
- [ ] Code is formatted and documented
- [ ] New features include relevant tests

---

Thanks for helping improve the Online Bookstore! 📚
