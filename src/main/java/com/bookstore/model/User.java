package com.bookstore.model;


public class User {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String role; // CUSTOMER, ADMIN, GUEST

    public User() {}

    public User(int userId, String username, String password, String email, String fullName, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
    }

    // --- Factory method for guest user ---
    public static User createGuest() {
        return new User(0, "guest", "", "guest@bookstore.com", "Guest User", "GUEST");
    }

    // --- Getters & Setters ---
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isGuest() {
        return "GUEST".equalsIgnoreCase(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
