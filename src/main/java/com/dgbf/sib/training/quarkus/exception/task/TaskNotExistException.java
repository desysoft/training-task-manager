package com.dgbf.sib.training.quarkus.exception.task;

public class TaskNotExistException extends RuntimeException {
    public TaskNotExistException(String message) {
        super(message);
    }
}
