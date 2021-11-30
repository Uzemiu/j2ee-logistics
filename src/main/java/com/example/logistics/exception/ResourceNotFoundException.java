package com.example.logistics.exception;

public class ResourceNotFoundException extends BadRequestException{

    public ResourceNotFoundException(){super();}

    public ResourceNotFoundException(String message){super(message);}
}
