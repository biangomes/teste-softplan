package br.beanascigom.testesoftplan.service;

import br.beanascigom.testesoftplan.model.Pessoa;
import br.beanascigom.testesoftplan.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository repo;

    public Pessoa buscaPessoaPorId(Long id) {
        return repo.findById(id).orElse(null);
    }
}
