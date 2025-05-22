package com.example.trains.exceptions;

public class NotAdminException extends Exception{
    public NotAdminException(String message){
        super(message);
    }

    public NotAdminException(String message, Throwable cause){
        super(message, cause);
    }

    public NotAdminException(Throwable cause){
        super(cause);
    }
}
