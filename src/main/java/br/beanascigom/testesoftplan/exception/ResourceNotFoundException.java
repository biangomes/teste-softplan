package br.beanascigom.testesoftplan.exception;

public class ResourceNotFoundException extends BusinessNotFoundException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
