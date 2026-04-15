package br.beanascigom.testesoftplan.exception;

import org.springframework.http.HttpStatus;

public class BusinessNotFoundException extends BusinessException {
    private static final long serialVersionUID = 1L;

    public BusinessNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
