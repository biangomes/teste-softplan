package br.beanascigom.testesoftplan.repository;

import br.beanascigom.testesoftplan.model.People;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<People, Long> {
    Optional<People> findByCpf(String cpf);
}
