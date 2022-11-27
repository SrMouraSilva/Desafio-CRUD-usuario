package br.com.srmourasilva.desafio.test.model;

import br.com.srmourasilva.desafio.model.Street;
import br.com.srmourasilva.desafio.sample.SampleModel;
import br.com.srmourasilva.desafio.validation.EasyValidator;
import br.com.srmourasilva.desafio.validation.mesage.ErrorMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StreetTest {
    @Test
    public void sampleStreetIsValid() {
        Street sampleStreet = SampleModel.sampleStreet();

        EasyValidator.Result<Street> violations = new EasyValidator().validate(sampleStreet);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void equals() {
        Street sampleStreet = SampleModel.sampleStreet();
        Street sampleStreetOtherInstance = SampleModel.sampleStreet();
        Street sampleStreetWithDifferentValue = SampleModel.sampleStreet();

        sampleStreetWithDifferentValue.setComplement("A");

        assertEquals(sampleStreet, sampleStreet);
        assertEquals(sampleStreet, sampleStreetOtherInstance);

        assertNotEquals(sampleStreet, sampleStreetWithDifferentValue);
    }

    @Test
    public void constructorWithCopy() {
        Street sampleStreet = SampleModel.sampleStreet();
        Street sampleStreetDeepCopy = new Street(sampleStreet);

        assertEquals(sampleStreet, sampleStreetDeepCopy);
        assertNotSame(sampleStreet, sampleStreetDeepCopy);
    }

    @Test
    public void zipCodeRequired() {
        Street street = SampleModel.sampleStreet();
        street.setZipCode(null);

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void zipCodeMinSize() {
        Street street = SampleModel.sampleStreet();
        street.setZipCode("605");

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.contains(ErrorMessage.MIN_SIZE));
    }

    @Test
    public void nameRequired() {
        Street street = SampleModel.sampleStreet();
        street.setName(null);

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void nameNotBlank() {
        Street street = SampleModel.sampleStreet();
        street.setName(" ");

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.contains(ErrorMessage.NOT_BLANK));
    }

    @Test
    public void numberNullable() {
        Street street = SampleModel.sampleStreet();
        street.setNumber(null);

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);
        System.out.println("test");

        assertTrue(violations.isEmpty());
    }

    @Test
    public void numberNotNull() {
        Street street = SampleModel.sampleStreet();
        street.setNumber(0);

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.contains(ErrorMessage.POSITIVE));
    }

    @Test
    public void numberNotNegative() {
        Street street = SampleModel.sampleStreet();
        street.setNumber(-1);

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.contains(ErrorMessage.POSITIVE));
    }

    @Test
    public void complementNullable() {
        Street street = SampleModel.sampleStreet();
        street.setComplement(null);

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void complementNotBlank() {
        Street street = SampleModel.sampleStreet();
        street.setComplement(" ");

        EasyValidator.Result<Street> violations = new EasyValidator().validate(street);

        assertTrue(violations.contains(ErrorMessage.NOT_BLANK));
    }
}
