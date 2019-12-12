package com.dgbf.sib.training.quarkus.exception.task;

public class TaskExistException extends RuntimeException {
    public TaskExistException(String message) {
        super(message);
    }
}
