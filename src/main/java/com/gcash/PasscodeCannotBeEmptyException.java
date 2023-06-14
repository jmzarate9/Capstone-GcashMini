package com.gcash;

public class PasscodeCannotBeEmptyException extends Exception {
    public PasscodeCannotBeEmptyException(String message) {
        super(message);
    }
}
