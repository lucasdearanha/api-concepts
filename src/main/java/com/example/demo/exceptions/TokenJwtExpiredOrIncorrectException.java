package com.example.demo.exceptions;

public class TokenJwtExpiredOrIncorrectException extends RuntimeException {
    public TokenJwtExpiredOrIncorrectException(String message) {
        super(message);
    }
}
