package br.beanascigom.testesoftplan.repository;

import br.beanascigom.testesoftplan.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    boolean existsByCpf(String cpf);

    boolean existsByCpfAndIdNot(String cpf, Long id);

    Optional<Pessoa> findByCpf(String cpf);
}
