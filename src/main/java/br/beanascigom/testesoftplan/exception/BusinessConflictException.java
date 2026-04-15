package br.beanascigom.testesoftplan.exception;

import org.springframework.http.HttpStatus;

public class BusinessConflictException extends BusinessException {
    private static final long serialVersionUID = 1L;

    public BusinessConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
