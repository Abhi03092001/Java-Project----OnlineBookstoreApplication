package com.bookstore.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Book {
    private int bookId;
    private String isbn;
    private String title;
    private String author;
    private String category;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private LocalDateTime createdAt;

    public Book() {}

    public Book(int bookId, String isbn, String title, String author,
                String category, String description,
                BigDecimal price, int stockQuantity, LocalDateTime createdAt) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
    }

    // --- Getters & Setters ---
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // --- Helpers ---
    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public void reduceStock(int quantity) {
        if (quantity <= stockQuantity) {
            stockQuantity -= quantity;
        }
    }

    public String toDetailedString() {
        return "\n--- BOOK DETAILS ---" +
                "\nID: " + bookId +
                "\nISBN: " + isbn +
                "\nTitle: " + title +
                "\nAuthor: " + author +
                "\nCategory: " + category +
                "\nDescription: " + description +
                "\nPrice: $" + price +
                "\nStock: " + stockQuantity +
                "\nAdded On: " + (createdAt != null ? createdAt.toString() : "N/A");
    }

    @Override
    public String toString() {
        return bookId + " - " + title + " by " + author + " ($" + price + ")";
    }
}
