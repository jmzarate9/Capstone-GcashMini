package com.gcash;

public class NumberCannotBeEmptyException extends Exception {
    public NumberCannotBeEmptyException(String message) {
        super(message);
    }
}
