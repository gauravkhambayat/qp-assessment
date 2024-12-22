package com.example.qp.assessment.grocery.exception;

public class InvalidInventoryException extends RuntimeException {

    public InvalidInventoryException(String message) {
        super(message);
    }
}