package br.beanascigom.testesoftplan.controller;

import br.beanascigom.testesoftplan.dto.PessoaRequestV2DTO;
import br.beanascigom.testesoftplan.dto.PessoaResponseDTO;
import br.beanascigom.testesoftplan.service.PessoaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/pessoas")
public class PessoaV2Controller {

    @Autowired
    private PessoaService service;

    @PostMapping("/")
    public ResponseEntity<PessoaResponseDTO> criar(@Valid @RequestBody PessoaRequestV2DTO request) throws JsonProcessingException {
        PessoaResponseDTO pessoa = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PessoaRequestV2DTO request) {
        PessoaResponseDTO pessoa = service.atualizar(id, request);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> buscaPessoaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscaPessoaPorId(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<PessoaResponseDTO>> listarPessoas() {
        return ResponseEntity.ok(service.listarPessoas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return ResponseEntity.ok(service.deletarPessoa(id));
    }
}
