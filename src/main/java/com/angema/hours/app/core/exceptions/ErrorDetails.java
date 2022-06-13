package com.angema.hours.app.core.exceptions;

public class ErrorDetails {
    public String message;
    public String details;

    public ErrorDetails(String message, String details) {
        super();
        this.message = message;
        this.details = details;
    }
}
