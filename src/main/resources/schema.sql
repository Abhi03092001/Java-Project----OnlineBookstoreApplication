-- Create DB
CREATE DATABASE IF NOT EXISTS Onlinebookstore_db;
USE Onlinebookstore_db;
-- Create Database
CREATE DATABASE IF NOT EXISTS bookstore_db;
USE bookstore_db;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    full_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Books Table
CREATE TABLE IF NOT EXISTS books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    price DECIMAL(10, 2) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    description TEXT,
    stock_quantity INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Orders Table
CREATE TABLE IF NOT EXISTS orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    total_amount DECIMAL(10, 2) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'PENDING',
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Order Items Table
CREATE TABLE IF NOT EXISTS order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    book_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

-- Insert Sample Books
INSERT INTO books (title, author, category, price, isbn, description, stock_quantity) VALUES
('Clean Code', 'Robert C. Martin', 'Programming', 42.99, '978-0132350884', 'A Handbook of Agile Software Craftsmanship', 50),
('Design Patterns', 'Gang of Four', 'Programming', 54.99, '978-0201633610', 'Elements of Reusable Object-Oriented Software', 30),
('The Pragmatic Programmer', 'Andrew Hunt', 'Programming', 39.99, '978-0135957059', 'Your Journey to Mastery', 40),
('Introduction to Algorithms', 'Thomas H. Cormen', 'Computer Science', 89.99, '978-0262033848', 'Comprehensive algorithms textbook', 25),
('Effective Java', 'Joshua Bloch', 'Programming', 49.99, '978-0134685991', 'Best practices for Java programming', 35),
('Head First Java', 'Kathy Sierra', 'Programming', 44.99, '978-0596009205', 'Brain-friendly guide to Java', 45),
('Java Concurrency in Practice', 'Brian Goetz', 'Programming', 52.99, '978-0321349606', 'Essential guide to concurrent programming', 20),
('Refactoring', 'Martin Fowler', 'Programming', 47.99, '978-0134757599', 'Improving the Design of Existing Code', 30),
('The Mythical Man-Month', 'Frederick Brooks', 'Software Engineering', 34.99, '978-0201835953', 'Essays on Software Engineering', 15),
('Code Complete', 'Steve McConnell', 'Programming', 49.99, '978-0735619678', 'A Practical Handbook of Software Construction', 28);
('Working Effectively with Legacy Code', 'Michael Feathers', 'Programming', 45.99, '978-0131177055', 'Techniques to modify and improve legacy systems safely', 20),
('Continuous Delivery', 'Jez Humble', 'DevOps', 55.99, '978-0321601919', 'Reliable software releases through build, test, and deployment automation', 18),
('Site Reliability Engineering', 'Niall Richard Murphy', 'DevOps', 59.99, '978-1491929124', 'Google’s approach to running reliable production systems', 22),
('Building Microservices', 'Sam Newman', 'Architecture', 48.99, '978-1491950357', 'Designing fine-grained systems', 30),
('Cloud Native Java', 'Josh Long', 'Cloud', 46.99, '978-1449374648', 'Building resilient apps with Spring Boot, Spring Cloud, and Cloud Foundry', 25),
('Docker Deep Dive', 'Nigel Poulton', 'DevOps', 42.99, '978-1521822807', 'Comprehensive guide to Docker internals and usage', 40),
('Kubernetes in Action', 'Marko Luksa', 'Cloud', 57.99, '978-1617293726', 'Deep dive into Kubernetes orchestration', 20),
('Database System Concepts', 'Abraham Silberschatz', 'Database', 64.99, '978-0078022159', 'Foundations of database management systems', 15),
('MongoDB: The Definitive Guide', 'Kristina Chodorow', 'Database', 44.99, '978-1449344689', 'Powerful and flexible NoSQL database guide', 28),
('Learning SQL', 'Alan Beaulieu', 'Database', 38.99, '978-0596520830', 'A hands-on guide to SQL fundamentals', 35),
('Artificial Intelligence: A Modern Approach', 'Stuart Russell', 'AI/ML', 84.99, '978-0134610993', 'Comprehensive AI textbook', 12),
('Deep Learning', 'Ian Goodfellow', 'AI/ML', 72.99, '978-0262035613', 'Foundational book on deep learning', 15),
('Hands-On Machine Learning with Scikit-Learn, Keras, and TensorFlow', 'Aurélien Géron', 'AI/ML', 58.99, '978-1492032649', 'Practical ML with Python libraries', 25),
('Python Crash Course', 'Eric Matthes', 'Programming', 39.99, '978-1593276034', 'Hands-on introduction to Python', 40),
('Fluent Python', 'Luciano Ramalho', 'Programming', 52.99, '978-1491946008', 'Writing idiomatic Pythonic code', 20),
('You Don’t Know JS Yet', 'Kyle Simpson', 'Programming', 34.99, '978-1091210099', 'Deep dive into JavaScript concepts', 30),
('Eloquent JavaScript', 'Marijn Haverbeke', 'Programming', 37.99, '978-1593279509', 'Modern introduction to JavaScript programming', 25),
('C Programming Language', 'Brian Kernighan, Dennis Ritchie', 'Programming', 44.99, '978-0131103627', 'Classic C programming guide', 18),
('Operating System Concepts', 'Abraham Silberschatz', 'Computer Science', 79.99, '978-1119456339', 'Modern operating systems theory and design', 14),
('Computer Networking: A Top-Down Approach', 'James Kurose, Keith Ross', 'Computer Science', 69.99, '978-0136681557', 'Comprehensive networking concepts explained', 20);