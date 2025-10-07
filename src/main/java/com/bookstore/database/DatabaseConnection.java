package com.bookstore.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/newbookstore_db"; // Change to your DB name
    private static final String USER = "newBook_user"; // Change to your DB username
    private static final String PASSWORD = "StrongPass123"; // Change to your DB password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
