package br.com.srmourasilva.desafio.model;

import java.util.Optional;

public class Street {
    private String zipCode;
    private String name;
    private Optional<Integer> number = Optional.empty();
    private Optional<String> complement = Optional.empty();

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<Integer> getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = Optional.ofNullable(number);
    }

    public Optional<String> getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = Optional.ofNullable(complement);
    }
}
