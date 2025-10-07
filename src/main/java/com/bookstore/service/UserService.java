package com.bookstore.service;

import com.bookstore.service.DynamoDBRecommendationService;
import com.bookstore.model.Recommendation;

import com.bookstore.model.Recommendation;

import com.bookstore.model.User;
import com.bookstore.util.PasswordUtil;

import java.sql.*;

public class UserService {

    public boolean usernameExists(String username) {
        // minimal stub
        return false;
    }

    public User register(String username, String password, String email, String fullName) {
        // minimal stub that "inserts" and returns a user
        User u = new User();
        u.setUsername(username);
        u.setFullName(fullName);
        u.setEmail(email);
        u.setRole("CUSTOMER");
        
        try {
            DynamoDBRecommendationService ddb = new DynamoDBRecommendationService();
            ddb.saveRecommendation(username, new Recommendation("Clean Code", 0.85));
            ddb.saveRecommendation(username, new Recommendation("Effective Java", 0.80));
            ddb.saveRecommendation(username, new Recommendation("The Pragmatic Programmer", 0.78));
        } catch (Exception e) {
            System.err.println("Warning: failed to seed DynamoDB recommendations for " + username + " -> " + e.getMessage());
        }
        return u;
    }

    public User login(String username, String password) { return null; }

    public void viewAllUsers() {}
}
