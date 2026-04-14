package br.beanascigom.testesoftplan.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<ICpfValidator, String> {
    private boolean allowMask;

    @Override
    public void initialize(ICpfValidator constraintAnnotation) {
        this.allowMask = constraintAnnotation.allowMask();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        String cpf = allowMask ? value.replaceAll("\\D", "") : value;

        if (!cpf.matches("\\d{11}")) {
            return false;
        }

        if (allDigitsAreEqual(cpf)) {
            return false;
        }

        return calculateCheckDigit(cpf, 9) == Character.getNumericValue(cpf.charAt(9))
                && calculateCheckDigit(cpf, 10) == Character.getNumericValue(cpf.charAt(10));
    }

    private boolean allDigitsAreEqual(String cpf) {
        char first = cpf.charAt(0);
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != first) {
                return false;
            }
        }
        return true;
    }

    private int calculateCheckDigit(String cpf, int baseSize) {
        int sum = 0;
        int weight = baseSize + 1;

        for (int i = 0; i < baseSize; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (weight - i);
        }

        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }
}