package br.beanascigom.testesoftplan.exception;

import org.springframework.http.HttpStatus;

public abstract class BusinessCheckedException extends Exception {
    private static final long serialVersionUID = 1L;

    private final HttpStatus status;

    protected BusinessCheckedException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
