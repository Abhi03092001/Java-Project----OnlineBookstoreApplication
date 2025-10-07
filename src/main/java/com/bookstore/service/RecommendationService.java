package com.bookstore.service;

import com.bookstore.model.Book;

import java.util.ArrayList;
import java.util.List;

public class RecommendationService {

    private final List<String> staticRecs = List.of(
            "Effective Java", "Refactoring", "The Pragmatic Programmer", "Design Patterns", "Head First Java"
    );

    public void generateRecommendationsBasedOnHistory(int userId, List<Book> history) {
        // No-op placeholder; in a real app, we'd use DynamoDB or analytics
    }

    public void displayRecommendations(int userId) {
        System.out.println("\n========================================");
        System.out.println("    RECOMMENDED BOOKS FOR YOU");
        System.out.println("========================================");
        int i = 1;
        for (String r : staticRecs) {
            System.out.println(i++ + ". " + r);
        }
        System.out.println("========================================");
    }
}
