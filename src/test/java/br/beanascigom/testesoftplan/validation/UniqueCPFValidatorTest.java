package br.beanascigom.testesoftplan.validation;

import br.beanascigom.testesoftplan.model.People;
import br.beanascigom.testesoftplan.repository.PeopleRepository;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UniqueCPFValidatorTest {

    @Test
    void shouldRejectDuplicatedCpfEvenWithDifferentMask() throws Exception {
        UniqueCPFValidator validator = new UniqueCPFValidator();
        injectRepository(validator, repositoryReturning(Optional.of(personWithIdAndCpf(10L, "52998224725"))));

        People duplicated = personWithIdAndCpf(20L, "529.982.247-25");

        assertFalse(validator.isValid(duplicated, null));
    }

    @Test
    void shouldAcceptSameCpfForSameRecord() throws Exception {
        UniqueCPFValidator validator = new UniqueCPFValidator();
        injectRepository(validator, repositoryReturning(Optional.of(personWithIdAndCpf(10L, "52998224725"))));

        People existing = personWithIdAndCpf(10L, "529.982.247-25");

        assertTrue(validator.isValid(existing, null));
    }

    @Test
    void shouldAcceptWhenCpfDoesNotExist() throws Exception {
        UniqueCPFValidator validator = new UniqueCPFValidator();
        injectRepository(validator, repositoryReturning(Optional.empty()));

        People newPerson = personWithIdAndCpf(null, "52998224725");

        assertTrue(validator.isValid(newPerson, null));
    }

    private void injectRepository(UniqueCPFValidator validator, PeopleRepository repository) throws Exception {
        Field field = UniqueCPFValidator.class.getDeclaredField("peopleRepository");
        field.setAccessible(true);
        field.set(validator, repository);
    }

    private PeopleRepository repositoryReturning(Optional<People> findByCpfResult) {
        return (PeopleRepository) Proxy.newProxyInstance(
                PeopleRepository.class.getClassLoader(),
                new Class[]{PeopleRepository.class},
                (proxy, method, args) -> {
                    if ("findByCpf".equals(method.getName())) {
                        return findByCpfResult;
                    }
                    if (method.getReturnType().equals(boolean.class)) {
                        return false;
                    }
                    return null;
                }
        );
    }

    private People personWithIdAndCpf(Long id, String cpf) {
        People people = new People();
        people.setId(id);
        people.setName("Test Person");
        people.setCpf(cpf);
        return people;
    }
}


