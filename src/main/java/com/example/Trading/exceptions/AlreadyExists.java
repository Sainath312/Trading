package com.example.Trading.exceptions;

public class AlreadyExists extends RuntimeException {
    public AlreadyExists(String message){
        super(message);
    }
}
