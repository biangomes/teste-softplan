package br.beanascigom.testesoftplan.controller;

import br.beanascigom.testesoftplan.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pessoa")
public class PessoaController {
    @Autowired
    private PessoaService service;

    @GetMapping("/v1/{id}")
    public ResponseEntity<Object> buscaPessoaV1(@PathVariable Long id) {
        var pessoa = service.buscaPessoaPorId(id);
        return new ResponseEntity<>(pessoa, HttpStatus.OK);
    }
}
