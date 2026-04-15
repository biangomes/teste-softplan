package br.beanascigom.testesoftplan.dto;

import br.beanascigom.testesoftplan.model.Sexo;
import br.beanascigom.testesoftplan.util.ICpfValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PessoaRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Nome obrigatorio")
    private String nome;

    private Sexo sexo;

    @Email(message = "Email invalido")
    @Size(max = 255, message = "Email deve ter no maximo 255 caracteres")
    private String email;

    @NotNull(message = "Data de nascimento obrigatorio")
    @Past(message = "Data de nascimento deve ser uma data passada")
    private LocalDate dataNascimento;

    @Size(max = 2, message = "Exemplo: SP, SC")
    private String estado;

    @Size(max = 255, message = "Pais deve ter no maximo 255 caracteres")
    private String pais;

    @NotBlank(message = "CPF obrigatorio")
    @ICpfValidator(message = "CPF invalido")
    private String cpf;
}
