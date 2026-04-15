package br.beanascigom.testesoftplan.exception;

public class ResourceNoContentException extends BusinessNotFoundException {
    private static final long serialVersionUID = 1L;

    public ResourceNoContentException(String message) {
        super(message);
    }
}
