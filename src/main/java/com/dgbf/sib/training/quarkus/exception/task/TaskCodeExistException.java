package com.dgbf.sib.training.quarkus.exception.task;

public class TaskCodeExistException extends RuntimeException {
    public TaskCodeExistException(String s) {
        super(s);
    }
}
