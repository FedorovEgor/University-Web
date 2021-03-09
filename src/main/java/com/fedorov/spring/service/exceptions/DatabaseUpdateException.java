package com.fedorov.spring.service.exceptions;

public class DatabaseUpdateException extends RuntimeException {

    public DatabaseUpdateException() {
        super();
    }

    public DatabaseUpdateException(String message) {
        super(message);
    }

    public DatabaseUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
