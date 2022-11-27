package br.com.srmourasilva.desafio.dto.user;

import br.com.srmourasilva.desafio.model.Street;
import io.swagger.v3.oas.annotations.media.Schema;
import org.openapitools.jackson.nullable.JsonNullable;

public class StreetUpdateRequestDTO {

    @Schema(description="Zip code or postcode", example="60521-025")
    private JsonNullable<String> zipCode = JsonNullable.undefined();

    @Schema(description="Name of the street", example="Rua Antônio Ivo")
    private JsonNullable<String> name = JsonNullable.undefined();

    @Schema(description="Number. `null` if it isn't applicable", example="290")
    private JsonNullable<Integer> number = JsonNullable.undefined();

    @Schema(description="Complement. `null` if it isn't applicable", example="A")
    private JsonNullable<String> complement = JsonNullable.undefined();


    public Street join(Street street) {
        Street newStreet = new Street(street);

        if (zipCode.isPresent()) {
            newStreet.setZipCode(zipCode.get());
        }
        if (name.isPresent()) {
            newStreet.setName(name.get());
        }
        if (number.isPresent()) {
            newStreet.setNumber(number.get());
        }
        if (complement.isPresent()) {
            newStreet.setComplement(complement.get());
        }

        return newStreet;
    }

    public JsonNullable<String> getZipCode() {
        return zipCode;
    }

    public void setZipCode(JsonNullable<String> zipCode) {
        this.zipCode = zipCode;
    }

    public JsonNullable<String> getName() {
        return name;
    }

    public void setName(JsonNullable<String> name) {
        this.name = name;
    }

    public JsonNullable<Integer> getNumber() {
        return number;
    }

    public void setNumber(JsonNullable<Integer> number) {
        this.number = number;
    }

    public JsonNullable<String> getComplement() {
        return complement;
    }

    public void setComplement(JsonNullable<String> complement) {
        this.complement = complement;
    }
}
