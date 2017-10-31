package com.springframework.exception;

/**
 * Created by Behrouz-ZD on 9/20/2017.
 */
public class RequiredArgumentException extends RuntimeException {

    private String argument;

    public RequiredArgumentException(String message,String argument) {
        super(message);
        this.argument = argument;
    }


}
