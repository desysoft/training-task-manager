package com.dgbf.sib.training.quarkus.exception.activity;

public class ActivityNotExistException extends RuntimeException {
    public ActivityNotExistException(String s) {
        super(s);
    }
}
