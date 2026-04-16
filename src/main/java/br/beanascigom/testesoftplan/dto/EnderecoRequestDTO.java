package br.beanascigom.testesoftplan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class EnderecoRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Cidade obrigatoria")
    @Size(max = 255, message = "Cidade deve ter no maximo 255 caracteres")
    private String cidade;

    @NotBlank(message = "Rua obrigatoria")
    @Size(max = 255, message = "Rua deve ter no maximo 255 caracteres")
    private String rua;

    @NotNull(message = "Numero obrigatorio")
    @Positive(message = "Numero deve ser maior que zero")
    private Integer numero;

    @NotBlank(message = "CEP obrigatorio")
    @Size(max = 20, message = "CEP deve ter no maximo 20 caracteres")
    private String cep;

    @NotBlank(message = "Bairro obrigatorio")
    @Size(max = 255, message = "Bairro deve ter no maximo 255 caracteres")
    private String bairro;

    @Size(max = 255, message = "Complemento deve ter no maximo 255 caracteres")
    private String complemento;
}
