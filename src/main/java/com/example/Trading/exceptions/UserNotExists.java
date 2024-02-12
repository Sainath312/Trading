package com.example.Trading.exceptions;

public class UserNotExists extends RuntimeException{
    public UserNotExists(String message) {
        super(message);
    }
}
