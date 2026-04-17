package br.beanascigom.testesoftplan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cidade;
    private String rua;
    private int numero;
    private String cep;
    private String bairro;
    private String complemento;
}

