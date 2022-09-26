package com.myproject.controller;

import java.time.LocalDateTime;

public class Error {

    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}