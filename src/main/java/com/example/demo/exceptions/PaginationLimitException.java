package com.example.demo.exceptions;

public class PaginationLimitException extends RuntimeException {
    public PaginationLimitException(String message) {
        super(message);
    }
}
