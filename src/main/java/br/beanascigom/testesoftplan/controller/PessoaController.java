package br.beanascigom.testesoftplan.controller;

import br.beanascigom.testesoftplan.config.SecurityConfig;
import br.beanascigom.testesoftplan.dto.PessoaRequestDTO;
import br.beanascigom.testesoftplan.dto.PessoaResponseDTO;
import br.beanascigom.testesoftplan.service.PessoaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/pessoas/v1", "/api/v1/pessoas"})
@Tag(name = "pessoas", description = "Controlador para salvar, ler, editar e deletar dados de pessoas")
@SecurityRequirement(name = SecurityConfig.BASIC_AUTH_SCHEME)
public class PessoaController {

    @Autowired
    private PessoaService service;

    @PostMapping("/")
    @Operation(summary = "Cria pessoas", description = "Endpoint para salvar dados de pessoas")
    @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso")
    @ApiResponse(responseCode = "401", description = "Usuário nao autorizado")
    @ApiResponse(responseCode = "409", description = "Dado unico ja cadastrado. Exemplo: CPF")
    @ApiResponse(responseCode = "400", description = "Falha de validacao no corpo da requisicao. Exemplo: CPF invalido")
    public ResponseEntity<PessoaResponseDTO> criar(@Valid @RequestBody PessoaRequestDTO request) throws JsonProcessingException {
        PessoaResponseDTO pessoa = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edita dados de pessoas", description = "Endpoint para editar dados de pessoas")
    @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso")
    @ApiResponse(responseCode = "401", description = "Usuário nao autorizado")
    @ApiResponse(responseCode = "409", description = "Dado unico ja cadastrado. Exemplo: CPF")
    @ApiResponse(responseCode = "400", description = "Falha de validacao no corpo da requisicao. Exemplo: CPF invalido")
    public ResponseEntity<PessoaResponseDTO> atualizar(@PathVariable Long id,
                                                       @Valid @RequestBody PessoaRequestDTO request) {
        PessoaResponseDTO pessoa = service.atualizar(id, request);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Cria pessoas", description = "Endpoint para buscar dados de pessoa por ID")
    @ApiResponse(responseCode = "200", description = "Pessoa com ID retornado")
    @ApiResponse(responseCode = "401", description = "Usuário nao autorizado")
    @ApiResponse(responseCode = "404", description = "Pessoa com ID nao encontrada")
    public ResponseEntity<PessoaResponseDTO> buscaPessoaPorId(@PathVariable Long id) {
        var pessoa = service.buscaPessoaPorId(id);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/")
    public ResponseEntity<List<PessoaResponseDTO>> listarPessoas() {
        return ResponseEntity.ok(service.listarPessoas());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta dados de pessoas", description = "Endpoint para deletar dados de pessoas por ID")
    @ApiResponse(responseCode = "204", description = "Pessoa deletada com sucesso")
    @ApiResponse(responseCode = "401", description = "Usuário nao autorizado")
    @ApiResponse(responseCode = "404", description = "Pessoa com ID nao encontrada")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarPessoa(id);
        return ResponseEntity.noContent().build();
    }
}
