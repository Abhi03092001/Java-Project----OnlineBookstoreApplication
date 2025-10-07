package com.bookstore.model;

import java.math.BigDecimal;

public class OrderItem {
    private int itemId;
    private int orderId;
    private Book book;
    private int quantity;
    private BigDecimal price;

    public OrderItem() {}

    public OrderItem(int itemId, int orderId, Book book, int quantity, BigDecimal price) {
        this.itemId = itemId;
        this.orderId = orderId;
        this.book = book;
        this.quantity = quantity;
        this.price = price;
    }

    // --- Getters & Setters ---
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemId=" + itemId +
                ", orderId=" + orderId +
                ", book=" + (book != null ? book.getTitle() : "null") +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
