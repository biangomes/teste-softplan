package br.beanascigom.testesoftplan.builder;

import br.beanascigom.testesoftplan.dto.PessoaResponseDTO;
import br.beanascigom.testesoftplan.model.Pessoa;
import br.beanascigom.testesoftplan.model.Sexo;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class PessoaBuilder {
    private PessoaBuilder pessoaBuilder;

    public static PessoaResponseDTO getPessoaBuilder() {
        return PessoaResponseDTO.builder()
                .id(1L)
                .nome("Maria")
                .sexo(Sexo.M)
                .email("maria@email.com")
                .dataNascimento(LocalDate.of(1990, 4, 15))
                .estado("SC")
                .pais("Brasil")
                .cpf("52998224725")
                .dataCriacao(OffsetDateTime.parse("2026-04-15T20:00:00-03:00"))
                .dataAtualizacao(OffsetDateTime.parse("2026-04-15T20:10:00-03:00"))
                .build();
    }
}
