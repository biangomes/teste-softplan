package br.beanascigom.testesoftplan.exception;

import org.springframework.http.HttpStatus;

public abstract class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final HttpStatus status;

    protected BusinessException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
