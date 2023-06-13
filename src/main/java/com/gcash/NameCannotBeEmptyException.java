package com.gcash;

public class NameCannotBeEmptyException extends Exception {
    public NameCannotBeEmptyException(String message) {
        super(message);
    }
}