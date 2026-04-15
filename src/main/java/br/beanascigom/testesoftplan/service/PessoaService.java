package br.beanascigom.testesoftplan.service;

import br.beanascigom.testesoftplan.exception.BusinessNotFoundException;
import br.beanascigom.testesoftplan.model.Pessoa;
import br.beanascigom.testesoftplan.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository repo;

    public Pessoa buscaPessoaPorId(Long id) {
        Optional<Pessoa> pessoa = repo.findById(id);
        if (pessoa.isEmpty()) {
            throw new BusinessNotFoundException("Pessoa nao encontrada.");
        }
        return pessoa.get();
    }
}
