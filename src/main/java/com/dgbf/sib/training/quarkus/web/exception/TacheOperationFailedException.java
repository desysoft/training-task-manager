package com.dgbf.sib.training.quarkus.web.exception;

public class TacheOperationFailedException extends RuntimeException {
    public TacheOperationFailedException(String s) {
        super(s);
    }
}
