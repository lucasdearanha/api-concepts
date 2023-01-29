package com.example.demo.exceptions;

public class UserOrPasswordInvalidException extends RuntimeException {
    public UserOrPasswordInvalidException(String message) {
        super(message);
    }
}
