package com.example.trains.exceptions;

public class LoginExistsException extends Exception{
    public LoginExistsException(String message){
        super(message);
    }

    public LoginExistsException(String message, Throwable cause){
        super(message, cause);
    }

    public LoginExistsException(Throwable cause){
        super(cause);
    }
}
