package com.bookstore.service;

import com.bookstore.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BrowsingHistoryService {
    private final List<Book> history = new ArrayList<>();

    public void addToHistory(Book book) { history.add(book); }

    public List<Book> getHistory() { return new ArrayList<>(history); }

    public void displayHistory() {
        if (history.isEmpty()) {
            System.out.println("\nNo browsing history yet.");
            return;
        }
        System.out.println("\n--- BROWSING HISTORY ---");
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
    }
}
