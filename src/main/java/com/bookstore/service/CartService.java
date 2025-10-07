package com.bookstore.service;

import com.bookstore.model.Book;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class CartService {
    private final Map<Book, Integer> cart = new LinkedHashMap<>();

    public Map<Book, Integer> getCart() { return cart; }

    public void addBook(Book book, int quantity) {
        cart.put(book, cart.getOrDefault(book, 0) + quantity);
        System.out.println("Added " + quantity + " x " + book.getTitle() + " to cart.");
    }

    public void updateQuantity(Book book, int quantity) {
        if (quantity <= 0) { cart.remove(book); System.out.println("Item removed."); }
        else { cart.put(book, quantity); System.out.println("Quantity updated."); }
    }

    public void removeBook(Book book) {
        cart.remove(book);
        System.out.println("Removed " + book.getTitle() + " from cart.");
    }

    public boolean isEmpty() { return cart.isEmpty(); }

    public int getItemCount() {
        return cart.values().stream().mapToInt(Integer::intValue).sum();
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Book, Integer> e : cart.entrySet()) {
            BigDecimal itemTotal = e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue()));
            total = total.add(itemTotal);
        }
        return total;
    }

    public void clearCart() {
        cart.clear();
    }

    public void displayCart() {
        if (cart.isEmpty()) {
            System.out.println("\n========================================");
            System.out.println("           SHOPPING CART");
            System.out.println("========================================");
            System.out.println("Your cart is empty.");
            System.out.println("========================================");
            return;
        }
        System.out.println("\n========================================");
        System.out.println("           SHOPPING CART");
        System.out.println("========================================");
        for (Map.Entry<Book, Integer> e : cart.entrySet()) {
            Book b = e.getKey();
            int q = e.getValue();
            BigDecimal subtotal = b.getPrice().multiply(BigDecimal.valueOf(q));
            System.out.printf("%s\n   Author: %s\n   Price: $%.2f x %d = $%.2f\n---\n",
                    b.getTitle(), b.getAuthor(), b.getPrice().doubleValue(), q, subtotal.doubleValue());
        }
        System.out.println("========================================");
        System.out.printf("TOTAL: $%.2f\n", calculateTotal().doubleValue());
        System.out.println("========================================");
    }
}
