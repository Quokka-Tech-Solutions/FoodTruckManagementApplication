package com.quokkatech.foodtruckmanagement.application.exceptions;

public class TruckAlreadyExistsException extends RuntimeException{
    public TruckAlreadyExistsException(String message) {
        super(message);
    }
}
