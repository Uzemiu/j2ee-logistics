package com.example.logistics.exception;

public class UnauthenticatedException extends BadRequestException{

    public UnauthenticatedException(){super();}

    public UnauthenticatedException(String message){super(message);}
}
