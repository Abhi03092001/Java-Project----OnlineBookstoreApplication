package com.bookstore.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private List<OrderItem> items;

    public Order() {}

    public Order(int orderId, int userId, BigDecimal totalAmount, LocalDateTime orderDate, List<OrderItem> items) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.items = items;
    }

    // --- Getters & Setters ---
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                ", items=" + (items != null ? items.size() : 0) +
                '}';
    }
}
