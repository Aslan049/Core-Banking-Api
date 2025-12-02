package com.aslankorkmaz.Core.Banking.API.exception;

public class CustomerAlreadyExists extends RuntimeException {
    public CustomerAlreadyExists(String message) {
        super(message);
    }
}
