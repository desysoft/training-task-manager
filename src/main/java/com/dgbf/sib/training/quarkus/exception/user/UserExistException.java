package com.dgbf.sib.training.quarkus.exception.user;

public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}
