package br.com.srmourasilva.desafio.model;

import br.com.srmourasilva.desafio.validation.annotation.NotBlankNullable;
import br.com.srmourasilva.desafio.validation.ErrorMessage;
import com.google.common.base.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Street {
    @NotNull(message=ErrorMessage.REQUIRED)
    @Size(min=4, message=ErrorMessage.MIN_SIZE)
    private String zipCode;

    @NotNull(message=ErrorMessage.REQUIRED)
    @NotBlankNullable
    private String name;

    @Min(value=1, message=ErrorMessage.POSITIVE)
    private Integer number;

    @NotBlankNullable
    private String complement;

    public Street() {}

    public Street(Street street) {
        this();

        setZipCode(street.zipCode);
        setName(street.name);
        setNumber(street.number);
        setComplement(street.complement);
    }

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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Street)) return false;
        Street street = (Street) o;
        return Objects.equal(getZipCode(), street.getZipCode()) && Objects.equal(getName(), street.getName()) && Objects.equal(getNumber(), street.getNumber()) && Objects.equal(getComplement(), street.getComplement());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getZipCode(), getName(), getNumber(), getComplement());
    }
}
