package br.com.srmourasilva.desafio.test.model;

import br.com.srmourasilva.desafio.model.Street;
import br.com.srmourasilva.desafio.sample.ModelBuilder;
import br.com.srmourasilva.desafio.util.EasyValidator;
import br.com.srmourasilva.desafio.validation.ErrorMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreetTest {

    @Test
    public void sampleStreetIsValid() {
        Street sampleStreet = ModelBuilder.sampleStreet();

        EasyValidator.Result<Street> violations = new EasyValidator().validate(sampleStreet);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void zipCodeRequired() {
        Street street = ModelBuilder.sampleStreet();
        street.setZipCode(null);

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void zipCodeMinSize() {
        Street street = ModelBuilder.sampleStreet();
        street.setZipCode("605");

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.contains(ErrorMessage.MIN_SIZE));
    }

    @Test
    public void nameRequired() {
        Street street = ModelBuilder.sampleStreet();
        street.setName(null);

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void nameNotBlank() {
        Street street = ModelBuilder.sampleStreet();
        street.setName(" ");

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.contains(ErrorMessage.NOT_BLANK));
    }

    @Test
    public void numberNullable() {
        Street street = ModelBuilder.sampleStreet();
        street.setNumber(null);

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);
        System.out.println("test");

        assertTrue(violations.isEmpty());
    }

    @Test
    public void numberNotNull() {
        Street street = ModelBuilder.sampleStreet();
        street.setNumber(0);

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.contains(ErrorMessage.POSITIVE));
    }

    @Test
    public void numberNotNegative() {
        Street street = ModelBuilder.sampleStreet();
        street.setNumber(-1);

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.contains(ErrorMessage.POSITIVE));
    }

    @Test
    public void complementNullable() {
        Street street = ModelBuilder.sampleStreet();
        street.setComplement(null);

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void complementNotBlank() {
        Street street = ModelBuilder.sampleStreet();
        street.setComplement(" ");

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.contains(ErrorMessage.NOT_BLANK));
    }
}
