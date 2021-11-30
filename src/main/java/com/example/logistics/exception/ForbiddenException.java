package com.example.logistics.exception;

public class ForbiddenException extends BadRequestException{

    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
