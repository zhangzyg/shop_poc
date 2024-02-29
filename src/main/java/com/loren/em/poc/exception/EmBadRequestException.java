package com.loren.em.poc.exception;

public class EmBadRequestException extends RuntimeException {

    public EmBadRequestException(String msg) {
        super(msg);
    }

    public EmBadRequestException(String msg, Throwable t) {
        super(msg, t);
    }
}
