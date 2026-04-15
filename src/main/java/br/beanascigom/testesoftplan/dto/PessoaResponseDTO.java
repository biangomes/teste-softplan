package br.beanascigom.testesoftplan.dto;

import br.beanascigom.testesoftplan.model.Sexo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private Sexo sexo;
    private String email;
    private LocalDate dataNascimento;
    private String estado;
    private String pais;
    private String cpf;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataAtualizacao;
}
