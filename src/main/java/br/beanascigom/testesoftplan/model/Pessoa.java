package br.beanascigom.testesoftplan.model;

import br.beanascigom.testesoftplan.util.ICpfValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
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

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column
    private Sexo sexo;

    @Email
    @Column(nullable = false)
    private String email;

    @Past
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column
    @Max(2)
    private String estado;

    @Column
    private String pais;

    @ICpfValidator
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
