package br.beanascigom.testesoftplan.model;

import br.beanascigom.testesoftplan.validation.CPFValid;
import br.beanascigom.testesoftplan.validation.UniqueCPF;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_people_cpf", columnNames = "cpf"))
@UniqueCPF
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Email
    private String email;

    @Past
    private LocalDate birth;

    private String state;

    private String country;

    @CPFValid
    @Column(length = 11, unique = true)
    private String cpf;

    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    @PreUpdate
    private void normalizeCpf() {
        if (cpf != null) {
            cpf = cpf.replaceAll("\\D", "");
        }
    }
}
