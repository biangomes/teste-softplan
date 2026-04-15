package br.beanascigom.testesoftplan.service;

import br.beanascigom.testesoftplan.dto.PessoaRequestDTO;
import br.beanascigom.testesoftplan.dto.PessoaResponseDTO;
import br.beanascigom.testesoftplan.exception.BusinessConflictException;
import br.beanascigom.testesoftplan.exception.BusinessNotFoundException;
import br.beanascigom.testesoftplan.model.Pessoa;
import br.beanascigom.testesoftplan.repository.PessoaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;


import java.util.List;

@Service
public class PessoaService {

    private static final Logger logger = LoggerFactory.getLogger(PessoaService.class);

    @Autowired
    private PessoaRepository repo;
    @Autowired
    private ObjectMapper mapper;

    public PessoaResponseDTO criar(PessoaRequestDTO request) {
        logger.info("Payload de entrada: {}", mapper.writeValueAsString(request));
        String cpfNormalizado = normalizaCpf(request.getCpf());
        validaUnificidadeCpf(cpfNormalizado, null);

        Pessoa pessoa = new Pessoa();
        convertePayloadParaEntidade(request, pessoa, cpfNormalizado);

        Pessoa saved = repo.save(pessoa);
        return toResponse(saved);
    }

    public PessoaResponseDTO atualizar(Long id, PessoaRequestDTO request) {
        Pessoa pessoa = repo.findById(id)
                .orElseThrow(() -> new BusinessNotFoundException("Pessoa nao encontrada."));

        String cpfNormalizado = normalizaCpf(request.getCpf());
        validaUnificidadeCpf(cpfNormalizado, id);

        convertePayloadParaEntidade(request, pessoa, cpfNormalizado);
        Pessoa saved = repo.save(pessoa);
        return toResponse(saved);
    }

    public PessoaResponseDTO buscaPessoaPorId(Long id) {
        Pessoa pessoa = repo.findById(id).orElseThrow(() -> new BusinessNotFoundException("Pessoa nao encontrada."));
        return toResponse(pessoa);
    }

    public List<PessoaResponseDTO> listarPessoas() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    private void validaUnificidadeCpf(String cpf, Long id) {
        logger.info("Validando se CPF é unico");
        boolean duplicado = id == null ? repo.existsByCpf(cpf) : repo.existsByCpfAndIdNot(cpf, id);
        logger.info("CPF unico: {}", duplicado);
        if (duplicado) {
            throw new BusinessConflictException("CPF ja cadastrado.");
        }
    }

    private String normalizaCpf(String cpf) {
        logger.info("Normalizando CPF [{}]", cpf);
        return cpf == null ? null : cpf.replaceAll("\\D", "");
    }

    private void convertePayloadParaEntidade(PessoaRequestDTO request, Pessoa pessoa, String cpfNormalizado) {
        pessoa.setNome(request.getNome());
        pessoa.setSexo(request.getSexo());
        pessoa.setEmail(request.getEmail());
        pessoa.setDataNascimento(request.getDataNascimento());
        pessoa.setEstado(request.getEstado());
        pessoa.setPais(request.getPais());
        pessoa.setCpf(cpfNormalizado);
    }

    private PessoaResponseDTO toResponse(Pessoa pessoa) {
        return PessoaResponseDTO.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .sexo(pessoa.getSexo())
                .email(pessoa.getEmail())
                .dataNascimento(pessoa.getDataNascimento())
                .estado(pessoa.getEstado())
                .pais(pessoa.getPais())
                .cpf(pessoa.getCpf())
                .dataCriacao(pessoa.getDataCriacao())
                .dataAtualizacao(pessoa.getDataAtualizacao())
                .build();
    }
}
