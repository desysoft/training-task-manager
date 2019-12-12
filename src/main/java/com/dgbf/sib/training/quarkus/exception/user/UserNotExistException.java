package com.dgbf.sib.training.quarkus.exception.user;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String message) {
        super(message);
    }
}
