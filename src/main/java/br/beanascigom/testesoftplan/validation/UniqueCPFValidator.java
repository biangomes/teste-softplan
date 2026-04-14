package br.beanascigom.testesoftplan.validation;

import br.beanascigom.testesoftplan.model.People;
import br.beanascigom.testesoftplan.repository.PeopleRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UniqueCPFValidator implements ConstraintValidator<UniqueCPF, People> {

    @Autowired
    private PeopleRepository peopleRepository;

    @Override
    public boolean isValid(People value, ConstraintValidatorContext context) {
        if (value == null || value.getCpf() == null || value.getCpf().isBlank()) {
            return true;
        }

        String normalizedCpf = value.getCpf().replaceAll("\\D", "");

        Optional<People> existing = peopleRepository.findByCpf(normalizedCpf);
        if (existing.isEmpty()) {
            return true;
        }

        Long currentId = value.getId();
        Long existingId = existing.get().getId();
        return currentId != null && currentId.equals(existingId);
    }
}


