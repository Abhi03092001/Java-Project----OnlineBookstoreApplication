USE bookstore_db;

-- Default Admin User (password=admin123, BCrypt)
INSERT INTO users (username, password, email, full_name, role) VALUES
('admin', '$2a$10$Dow1T9GzJXy17dpL9Xhyge8U7jAfFb5EYemOqMJU6a3vDWVjKe2V2', 'admin@bookstore.com', 'Admin User', 'ADMIN');

-- Seed 30+ Books
INSERT INTO books (title, author, category, price, isbn, description, stock_quantity) VALUES
('Clean Code', 'Robert C. Martin', 'Programming', 42.99, '9780132350884', 'A Handbook of Agile Software Craftsmanship', 10),
('Effective Java', 'Joshua Bloch', 'Programming', 49.99, '9780134685991', 'Best practices for Java programming', 8),
('Refactoring', 'Martin Fowler', 'Programming', 55.00, '9780201485677', 'Improving the design of existing code', 5),
('Design Patterns', 'Erich Gamma', 'Programming', 47.99, '9780201633610', 'Elements of Reusable Object-Oriented Software', 6),
('Introduction to Algorithms', 'Cormen, Leiserson, Rivest, Stein', 'Programming', 75.00, '9780262033848', 'Algorithms bible', 4),
('The Pragmatic Programmer', 'Andrew Hunt, David Thomas', 'Programming', 39.99, '9780135957059', 'Your Journey to Mastery', 12),
('Java Concurrency in Practice', 'Brian Goetz', 'Programming', 52.99, '9780321349606', 'Concurrent programming in Java', 7),
('Head First Java', 'Kathy Sierra, Bert Bates', 'Programming', 40.00, '9780596009205', 'Brain-friendly Java learning', 10),
('Python Crash Course', 'Eric Matthes', 'Programming', 38.50, '9781593276034', 'Hands-on Python for beginners', 12),
('Fluent Python', 'Luciano Ramalho', 'Programming', 58.00, '9781491946008', 'Clear, concise Python techniques', 6),
('Database System Concepts', 'Silberschatz, Korth, Sudarshan', 'Databases', 60.00, '9780078022159', 'Database fundamentals', 7),
('NoSQL Distilled', 'Pramod J. Sadalage, Martin Fowler', 'Databases', 35.00, '9780321826626', 'Brief guide to NoSQL systems', 9),
('Cloud Native Java', 'Josh Long', 'Cloud/DevOps', 52.00, '9781449374648', 'Spring Boot and Cloud Foundry', 6),
('Kubernetes in Action', 'Marko Lukša', 'Cloud/DevOps', 58.00, '9781617293726', 'Kubernetes explained', 5),
('Docker Deep Dive', 'Nigel Poulton', 'Cloud/DevOps', 45.00, '9781521822807', 'Docker fundamentals', 8),
('Hands-On Machine Learning', 'Aurélien Géron', 'AI/ML', 64.00, '9781492032649', 'Scikit-Learn, Keras & TensorFlow', 6),
('Deep Learning', 'Ian Goodfellow, Yoshua Bengio, Aaron Courville', 'AI/ML', 70.00, '9780262035613', 'Comprehensive DL guide', 4),
('Artificial Intelligence: A Modern Approach', 'Stuart Russell, Peter Norvig', 'AI/ML', 72.00, '9780136042594', 'AI bible', 3),
('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', 'Fiction', 25.00, '9780590353427', 'First Harry Potter book', 15),
('Harry Potter and the Chamber of Secrets', 'J.K. Rowling', 'Fiction', 25.00, '9780439064873', 'Second Harry Potter book', 15),
('The Lord of the Rings', 'J.R.R. Tolkien', 'Fiction', 40.00, '9780544003415', 'Epic fantasy trilogy', 10),
('The Hobbit', 'J.R.R. Tolkien', 'Fiction', 30.00, '9780547928227', 'Prequel to LOTR', 12),
('Steve Jobs', 'Walter Isaacson', 'Non-Fiction', 28.00, '9781451648539', 'Biography of Steve Jobs', 9),
('Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', 'Non-Fiction', 32.00, '9780062316097', 'Human history exploration', 10),
('Educated', 'Tara Westover', 'Non-Fiction', 22.00, '9780399590504', 'Memoir of family and education', 8),
('Becoming', 'Michelle Obama', 'Non-Fiction', 25.00, '9781524763138', 'Memoir of former First Lady', 11),
('Atomic Habits', 'James Clear', 'Self-Help', 21.00, '9780735211292', 'Tiny changes, remarkable results', 20),
('The Power of Habit', 'Charles Duhigg', 'Self-Help', 20.00, '9780812981605', 'Why we do what we do', 15),
('Think and Grow Rich', 'Napoleon Hill', 'Self-Help', 18.00, '9781937879501', 'Success classic', 14),
('Rich Dad Poor Dad', 'Robert T. Kiyosaki', 'Finance', 19.00, '9781612680194', 'Personal finance classic', 18),
('The Intelligent Investor', 'Benjamin Graham', 'Finance', 30.00, '9780060555664', 'Value investing bible', 12),
('Principles', 'Ray Dalio', 'Finance', 35.00, '9781501124020', 'Life and work principles', 10),
('Zero to One', 'Peter Thiel', 'Business', 24.00, '9780804139298', 'Startups and future of innovation', 9),
('The Lean Startup', 'Eric Ries', 'Business', 26.00, '9780307887894', 'How today''s entrepreneurs use continuous innovation', 13);
