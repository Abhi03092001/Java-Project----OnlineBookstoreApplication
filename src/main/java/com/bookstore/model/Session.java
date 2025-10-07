package com.bookstore.model;

import java.time.LocalDateTime;

public class Session {
    private int userId;
    private String token;
    private LocalDateTime loginTime = LocalDateTime.now();

    public Session() {}

    public Session(int userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public LocalDateTime getLoginTime() { return loginTime; }
    public void setLoginTime(LocalDateTime loginTime) { this.loginTime = loginTime; }
}
