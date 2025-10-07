USE Onlinebookstore_db;

-- Insert Users (1 Admin, 3 Customers)
INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'ADMIN'),
('john_doe', 'password123', 'CUSTOMER'),
('alice_smith', 'alicepass', 'CUSTOMER'),
('bob_williams', 'bobpass', 'CUSTOMER');

-- Insert 30 Books
INSERT INTO books (title, author, price, stock) VALUES
('The Pragmatic Programmer', 'Andrew Hunt', 499.99, 20),
('Clean Code', 'Robert C. Martin', 599.99, 15),
('Effective Java', 'Joshua Bloch', 699.99, 10),
('Design Patterns', 'Erich Gamma', 799.99, 8),
('Head First Java', 'Kathy Sierra', 399.99, 25),
('Java Concurrency in Practice', 'Brian Goetz', 649.99, 12),
('Refactoring', 'Martin Fowler', 549.99, 18),
('Test Driven Development', 'Kent Beck', 459.99, 14),
('Working Effectively with Legacy Code', 'Michael Feathers', 629.99, 10),
('Introduction to Algorithms', 'Thomas H. Cormen', 899.99, 9),
('Cracking the Coding Interview', 'Gayle McDowell', 749.99, 20),
('Algorithms', 'Robert Sedgewick', 799.99, 7),
('Computer Networks', 'Andrew S. Tanenbaum', 679.99, 12),
('Operating System Concepts', 'Abraham Silberschatz', 849.99, 8),
('Database System Concepts', 'Henry F. Korth', 769.99, 10),
('Distributed Systems', 'George Coulouris', 719.99, 11),
('Artificial Intelligence', 'Stuart Russell', 899.99, 10),
('Machine Learning', 'Tom Mitchell', 659.99, 14),
('Deep Learning', 'Ian Goodfellow', 929.99, 6),
('Hands-On ML with Scikit-Learn & TensorFlow', 'Aurélien Géron', 799.99, 9),
('Python Crash Course', 'Eric Matthes', 449.99, 20),
('Learning Python', 'Mark Lutz', 649.99, 13),
('Fluent Python', 'Luciano Ramalho', 749.99, 8),
('Programming in C', 'Dennis Ritchie', 399.99, 30),
('C Programming Absolute Beginner’s Guide', 'Greg Perry', 349.99, 22),
('The C++ Programming Language', 'Bjarne Stroustrup', 699.99, 15),
('Effective C++', 'Scott Meyers', 589.99, 12),
('You Don’t Know JS', 'Kyle Simpson', 499.99, 18),
('Eloquent JavaScript', 'Marijn Haverbeke', 459.99, 25),
('JavaScript: The Good Parts', 'Douglas Crockford', 399.99, 20);

-- Sample Cart Entries
INSERT INTO cart (user_id, book_id, quantity) VALUES
(2, 1, 1),   -- john_doe -> Pragmatic Programmer
(2, 2, 1),   -- john_doe -> Clean Code
(3, 5, 2),   -- alice_smith -> Head First Java
(4, 10, 1);  -- bob_williams -> Intro to Algorithms

-- Sample Orders
INSERT INTO orders (user_id, total) VALUES
(2, 1099.98),   -- john_doe order
(3, 799.98),    -- alice_smith order
(4, 899.99);    -- bob_williams order

-- Sample Order Items
INSERT INTO order_items (order_id, book_id, quantity, price) VALUES
(1, 1, 1, 499.99),
(1, 2, 1, 599.99),
(2, 5, 2, 399.99),
(3, 10, 1, 899.99);
