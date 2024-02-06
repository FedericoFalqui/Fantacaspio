package com.federico.fantacaspio.exception;

public class NotEnoughCreditsException extends RuntimeException{
    public NotEnoughCreditsException(String message) {
        super(message);
    }

}
