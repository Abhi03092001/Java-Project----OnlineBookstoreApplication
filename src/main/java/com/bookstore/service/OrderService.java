package com.bookstore.service;

import com.bookstore.model.Book;
import com.bookstore.model.Order;
import com.bookstore.model.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderService {

    private final List<Order> orders = new ArrayList<>();
    private int orderCounter = 1;

    /**
     * Create a new order for the given user and cart items.
     */
    public Order createOrder(int userId, Map<Book, Integer> cart) {
        if (cart == null || cart.isEmpty()) {
            return null;
        }

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Book, Integer> entry : cart.entrySet()) {
            Book book = entry.getKey();
            int quantity = entry.getValue();
            BigDecimal price = book.getPrice().multiply(BigDecimal.valueOf(quantity));

            OrderItem item = new OrderItem(0, orderCounter, book, quantity, price);
            items.add(item);

            total = total.add(price);
        }

        Order order = new Order(orderCounter++, userId, total, LocalDateTime.now(), items);
        orders.add(order);
        return order;
    }

    /**
     * Return all orders of a user.
     */
    public List<Order> getOrderHistory(int userId) {
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserId() == userId) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    /**
     * Display order history on console.
     */
    public void displayOrderHistory(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            System.out.println("\nNo orders found.");
            return;
        }

        System.out.println("\n=== ORDER HISTORY ===");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Date: " + order.getOrderDate());
            System.out.println("Total: $" + order.getTotalAmount());
            for (OrderItem item : order.getItems()) {
                System.out.printf(" - %s x%d ($%.2f)%n",
                        item.getBook().getTitle(),
                        item.getQuantity(),
                        item.getPrice());
            }
            System.out.println("----------------------");
        }
    }

    public void viewSalesReport() {
    }
}
