package com.challenge.w2m.exception;

public class AuthorityNotFoundException extends RuntimeException{
    public AuthorityNotFoundException(String message) {
        super(message);
    }
}
