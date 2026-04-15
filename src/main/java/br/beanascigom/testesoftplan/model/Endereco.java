package br.beanascigom.testesoftplan.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {
    private String cidade;
    private String rua;
    private int numero;
    private String cep;
    private String bairro;
    private String complemento;
}
