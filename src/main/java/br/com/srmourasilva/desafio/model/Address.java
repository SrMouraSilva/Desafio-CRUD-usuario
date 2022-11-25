package br.com.srmourasilva.desafio.model;

import br.com.srmourasilva.desafio.validation.ErrorMessage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Address {
    @NotNull(message=ErrorMessage.REQUIRED)
    @Size(min=4, message=ErrorMessage.MIN_SIZE)
    private String country;

    /**
     * State or province
     */
    @NotNull(message=ErrorMessage.REQUIRED)
    @Size(min=3, message=ErrorMessage.MIN_SIZE)
    private String state;

    @NotNull(message=ErrorMessage.REQUIRED)
    private Street street;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }
}
