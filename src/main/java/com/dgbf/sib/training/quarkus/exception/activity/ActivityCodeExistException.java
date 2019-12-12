package com.dgbf.sib.training.quarkus.exception.activity;

public class ActivityCodeExistException extends RuntimeException {
    public ActivityCodeExistException(String s) {
        super(s);
    }
}
