package br.beanascigom.testesoftplan.controller;

import br.beanascigom.testesoftplan.dto.PessoaRequestDTO;
import br.beanascigom.testesoftplan.dto.PessoaResponseDTO;
import br.beanascigom.testesoftplan.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pessoas")
public class PessoaController {
    @Autowired
    private PessoaService service;

    @PostMapping("/v1/")
    public ResponseEntity<PessoaResponseDTO> criar(@Valid @RequestBody PessoaRequestDTO request) {
        PessoaResponseDTO pessoa = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
    }

    @PutMapping("/v1/{id}")
    public ResponseEntity<PessoaResponseDTO> atualizar(@PathVariable Long id,
                                                       @Valid @RequestBody PessoaRequestDTO request) {
        PessoaResponseDTO pessoa = service.atualizar(id, request);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<PessoaResponseDTO> buscaPessoaPorId(@PathVariable Long id) {
        var pessoa = service.buscaPessoaPorId(id);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/v1/")
    public ResponseEntity<List<PessoaResponseDTO>> listarPessoas() {
        return ResponseEntity.ok(service.listarPessoas());
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return ResponseEntity.ok(service.deletarPessoa(id));
    }
}
