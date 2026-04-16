package br.beanascigom.testesoftplan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SourceController {
    @GetMapping(path = "/source")
    public ResponseEntity<Object> source() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
