package br.beanascigom.testesoftplan.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaRequestV2DTO extends PessoaRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Valid
    @NotNull(message = "Endereco obrigatorio")
    private EnderecoRequestDTO endereco;
}

