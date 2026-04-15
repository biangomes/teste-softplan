package br.beanascigom.testesoftplan.exception;

import org.springframework.http.HttpStatus;

public class BusinessCheckedNotFoundException extends BusinessCheckedException {
    private static final long serialVersionUID = 1L;

    public BusinessCheckedNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
