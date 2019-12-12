package com.dgbf.sib.training.quarkus.exception.activity;

public class ActivityExistException extends RuntimeException {
    public ActivityExistException(String s) {
        super(s);
    }
}
