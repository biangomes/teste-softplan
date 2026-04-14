package br.beanascigom.testesoftplan.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String city;
    private String street;
    private String number;
    private String zipcode;
    private String country;
    private String neighborhood;
    private String complement;
}
