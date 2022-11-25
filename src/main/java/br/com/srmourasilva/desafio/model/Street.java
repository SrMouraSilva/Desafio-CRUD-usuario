package br.com.srmourasilva.desafio.model;

import br.com.srmourasilva.desafio.util.validation.NotBlankNullable;
import br.com.srmourasilva.desafio.validation.ErrorMessage;

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
}
