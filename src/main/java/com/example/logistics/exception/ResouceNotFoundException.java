package com.example.logistics.exception;

public class ResouceNotFoundException extends RuntimeException{

    public ResouceNotFoundException(){super();}

    public ResouceNotFoundException(String message){super(message);}
}
