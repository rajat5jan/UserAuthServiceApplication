package org.example.userauthservice.exceptions;

public class PasswordMissMatchException extends Exception{
    public PasswordMissMatchException(String message) {
        super(message);
    }
}
