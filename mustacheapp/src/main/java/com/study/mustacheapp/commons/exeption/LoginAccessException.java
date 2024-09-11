package com.study.mustacheapp.commons.exeption;

public class LoginAccessException extends RuntimeException {
    public LoginAccessException(String message) {
        super(message);
    }
}
