package br.com.srmourasilva.desafio.model;

import br.com.srmourasilva.desafio.validation.mesage.ErrorMessage;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Address {

    @Schema(description="Country name. Preferable follows ISO 3166", example="Brazil")
    @NotNull(message=ErrorMessage.REQUIRED)
    @Size(min=4, message=ErrorMessage.MIN_SIZE)
    private String country;

    @Schema(description="State, territorie or province", example="Ceará")
    @NotNull(message=ErrorMessage.REQUIRED)
    @Size(min=3, message=ErrorMessage.MIN_SIZE)
    private String state;

    @NotNull(message=ErrorMessage.REQUIRED)
    private Street street;

    public Address() {}

    public Address(Address address) {
        this();

        setCountry(address.country);
        setState(address.state);
        setStreet(new Street(address.street));
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equal(getCountry(), address.getCountry()) && Objects.equal(getState(), address.getState()) && Objects.equal(getStreet(), address.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCountry(), getState(), getStreet());
    }
}
