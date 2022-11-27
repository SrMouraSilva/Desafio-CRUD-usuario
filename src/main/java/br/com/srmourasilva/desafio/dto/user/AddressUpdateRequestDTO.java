package br.com.srmourasilva.desafio.dto.user;

import br.com.srmourasilva.desafio.model.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import org.openapitools.jackson.nullable.JsonNullable;

public class AddressUpdateRequestDTO {

    @Schema(description="Country name. Preferable follows ISO 3166", example="Brazil")
    private JsonNullable<String> country = JsonNullable.undefined();

    @Schema(description="State, territorie or province", example="Ceará")
    private JsonNullable<String> state = JsonNullable.undefined();

    private JsonNullable<StreetUpdateRequestDTO> street = JsonNullable.undefined();

    public Address join(Address address) {
        Address newAddress = new Address(address);

        if (country.isPresent()) {
            newAddress.setCountry(country.get());
        }
        if (state.isPresent()) {
            newAddress.setState(state.get());
        }
        if (street.isPresent()) {
            newAddress.setStreet(street.get().join(address.getStreet()));
        }

        return newAddress;
    }

    public JsonNullable<String> getCountry() {
        return country;
    }

    public void setCountry(JsonNullable<String> country) {
        this.country = country;
    }

    public JsonNullable<String> getState() {
        return state;
    }

    public void setState(JsonNullable<String> state) {
        this.state = state;
    }

    public JsonNullable<StreetUpdateRequestDTO> getStreet() {
        return street;
    }

    public void setStreet(JsonNullable<StreetUpdateRequestDTO> street) {
        this.street = street;
    }
}
