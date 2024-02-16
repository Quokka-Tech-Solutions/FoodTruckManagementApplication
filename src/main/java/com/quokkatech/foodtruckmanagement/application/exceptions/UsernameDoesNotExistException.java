package com.quokkatech.foodtruckmanagement.application.exceptions;

public class UsernameDoesNotExistException extends RuntimeException{
    public UsernameDoesNotExistException(String message) {
        super(message);
    }
}
