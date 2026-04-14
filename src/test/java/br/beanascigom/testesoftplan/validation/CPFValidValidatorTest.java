package br.beanascigom.testesoftplan.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CPFValidValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void shouldAcceptValidCpfWithoutMask() {
        Document document = new Document("52998224725");

        assertEquals(0, validator.validate(document).size());
    }

    @Test
    void shouldAcceptValidCpfWithMask() {
        Document document = new Document("529.982.247-25");

        assertEquals(0, validator.validate(document).size());
    }

    @Test
    void shouldRejectCpfWithInvalidCheckDigits() {
        Document document = new Document("52998224724");

        assertEquals(1, validator.validate(document).size());
    }

    @Test
    void shouldRejectCpfWithAllDigitsEqual() {
        Document document = new Document("11111111111");

        assertEquals(1, validator.validate(document).size());
    }

    @Test
    void shouldRejectCpfWithInvalidLength() {
        Document document = new Document("1234567890");

        assertEquals(1, validator.validate(document).size());
    }

    private static class Document {

        @CPFValid
        private final String cpf;

        private Document(String cpf) {
            this.cpf = cpf;
        }
    }
}


