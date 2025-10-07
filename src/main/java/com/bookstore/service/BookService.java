package com.bookstore.service;

import com.bookstore.database.DatabaseConnection;
import com.bookstore.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class BookService {

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY title";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) { books.add(extractBookFromResultSet(rs)); }
        } catch (SQLException e) { System.err.println("Error fetching books: " + e.getMessage()); }
        return books;
    }

    public List<Book> listAllBooksSortedByTitle() {
        List<Book> books = getAllBooks();
        books.sort(Comparator.comparing(Book::getTitle));
        return books;
    }

    public List<Book> listAllBooksSortedByPriceAsc() {
        List<Book> books = getAllBooks();
        books.sort(Comparator.comparing(b -> b.getPrice()));
        return books;
    }

    public List<Book> listAllBooksSortedByPriceDesc() {
        List<Book> books = getAllBooks();
        books.sort(Comparator.comparing((Book b) -> b.getPrice()).reversed());
        return books;
    }

    public List<Book> lowStockAlerts(int threshold) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE stock_quantity <= ? ORDER BY stock_quantity ASC, title ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, threshold);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) books.add(extractBookFromResultSet(rs));
        } catch (SQLException e) { System.err.println("Error fetching low stock: " + e.getMessage()); }
        return books;
    }

    public List<Book> searchBooks(String searchTerm) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR category LIKE ? ORDER BY title";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) { books.add(extractBookFromResultSet(rs)); }
        } catch (SQLException e) { System.err.println("Error searching books: " + e.getMessage()); }
        return books;
    }

    public List<Book> filterByCategory(String category) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE category = ? ORDER BY title";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) { books.add(extractBookFromResultSet(rs)); }
        } catch (SQLException e) { System.err.println("Error fetching books by category: " + e.getMessage()); }
        return books;
    }

    public Book getBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE book_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) { return extractBookFromResultSet(rs); }
        } catch (SQLException e) { System.err.println("Error fetching book: " + e.getMessage()); }
        return null;
    }

    public boolean updateBookDetails(int bookId, Double price, Integer stock, String description) {
        StringBuilder sql = new StringBuilder("UPDATE books SET ");
        List<Object> params = new ArrayList<>();
        if (price != null) { sql.append("price = ?, "); params.add(price); }
        if (stock != null) { sql.append("stock_quantity = ?, "); params.add(stock); }
        if (description != null) { sql.append("description = ?, "); params.add(description); }
        if (params.isEmpty()) return false;
        sql.setLength(sql.length() - 2); // remove last comma
        sql.append(" WHERE book_id = ?");
        params.add(bookId);
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { System.err.println("Error updating book: " + e.getMessage()); }
        return false;
    }

    public boolean removeBook(int bookId) {
        String sql = "DELETE FROM books WHERE book_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { System.err.println("Error removing book: " + e.getMessage()); }
        return false;
    }

    public boolean updateStock(int bookId, int quantity) {
        String sql = "UPDATE books SET stock_quantity = stock_quantity - ? WHERE book_id = ? AND stock_quantity >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, bookId);
            pstmt.setInt(3, quantity);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating stock: " + e.getMessage());
        }
        return false;
    }

    // ===== Admin CLI helpers =====
    public void addBookCLI(Scanner scanner) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("\n--- ADD NEW BOOK ---");
            System.out.print("Title: "); String title = scanner.nextLine();
            System.out.print("Author: "); String author = scanner.nextLine();
            System.out.print("Category: "); String category = scanner.nextLine();
            System.out.print("Price: "); double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Stock: "); int stock = Integer.parseInt(scanner.nextLine());
            System.out.print("ISBN (optional): "); String isbn = scanner.nextLine();
            System.out.print("Description (optional): "); String desc = scanner.nextLine();
            String sql = "INSERT INTO books (title, author, category, price, stock_quantity, isbn, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, category);
            ps.setDouble(4, price);
            ps.setInt(5, stock);
            ps.setString(6, isbn);
            ps.setString(7, desc);
            ps.executeUpdate();
            System.out.println("✓ Book added successfully!");
        } catch (Exception e) {
            System.err.println("Error adding book: " + e.getMessage());
        }
    }

    public void removeBookCLI(Scanner scanner) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter Book ID to remove: ");
            int id = Integer.parseInt(scanner.nextLine());
            String sql = "DELETE FROM books WHERE book_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("✓ Book removed.");
            else System.out.println("No book found with that ID.");
        } catch (Exception e) {
            System.err.println("Error removing book: " + e.getMessage());
        }
    }

    public void updateStockCLI(Scanner scanner) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter Book ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter New Stock: ");
            int stock = Integer.parseInt(scanner.nextLine());
            String sql = "UPDATE books SET stock_quantity = ? WHERE book_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, stock);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("✓ Stock updated.");
            else System.out.println("No book found with that ID.");
        } catch (Exception e) {
            System.err.println("Error updating stock: " + e.getMessage());
        }
    }

    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setCategory(rs.getString("category"));
        book.setPrice(rs.getBigDecimal("price"));
        book.setIsbn(rs.getString("isbn"));
        book.setDescription(rs.getString("description"));
        book.setStockQuantity(rs.getInt("stock_quantity"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) book.setCreatedAt(ts.toLocalDateTime());
        return book;
    }
}
