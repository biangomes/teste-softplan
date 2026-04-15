package br.beanascigom.testesoftplan.exception;

import org.springframework.http.HttpStatus;

public class BusinessCheckedConflictException extends BusinessCheckedException {
    private static final long serialVersionUID = 1L;

    public BusinessCheckedConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
