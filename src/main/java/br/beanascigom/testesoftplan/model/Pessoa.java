package br.beanascigom.testesoftplan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public", name = "pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column
    private Sexo sexo;

    @Column(nullable = false)
    private String email;

    @Column
    private LocalDate dataNascimento;

    @Column
    private String estado;

    @Column
    private String pais;

    @Column(nullable = false)
    private String cpf;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dataCriacao;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataAtualizacao;
}
