package br.beanascigom.testesoftplan.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String cidade;
    private String rua;
    private int numero;
    private String cep;
    private String bairro;
    private String complemento;
}
