package br.com.srmourasilva.desafio.test.model;

import br.com.srmourasilva.desafio.model.Address;
import br.com.srmourasilva.desafio.sample.SampleModel;
import br.com.srmourasilva.desafio.validation.EasyValidator;
import br.com.srmourasilva.desafio.validation.ErrorMessage;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {

    @Test
    public void sampleAddressIsValid() {
        Address sampleAddress = SampleModel.sampleAddress();

        EasyValidator.Result<Address> violations = new EasyValidator().validate(sampleAddress);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void equals() {
        Address sampleAddress = SampleModel.sampleAddress();
        Address sampleAddressOtherInstance = SampleModel.sampleAddress();
        Address sampleAddressWithDifferentValue = SampleModel.sampleAddress();

        sampleAddressWithDifferentValue.setCountry(UUID.randomUUID().toString());

        assertEquals(sampleAddress, sampleAddress);
        assertEquals(sampleAddress, sampleAddressOtherInstance);

        assertNotEquals(sampleAddress, sampleAddressWithDifferentValue);
    }

    @Test
    public void constructorWithCopy() {
        Address sampleAddress = SampleModel.sampleAddress();
        Address sampleAddressDeepCopy = SampleModel.sampleAddress();

        assertEquals(sampleAddress, sampleAddressDeepCopy);
        assertNotSame(sampleAddress, sampleAddressDeepCopy);
    }

    @Test
    public void countryRequired() {
        Address address = SampleModel.sampleAddress();
        address.setCountry(null);

        EasyValidator.Result<Address> violations = new EasyValidator().validate(address);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void countryMinSize() {
        Address address = SampleModel.sampleAddress();
        address.setCountry("Per"); // instead of Peru

        EasyValidator.Result<Address> violations = new EasyValidator().validate(address);

        assertTrue(violations.contains(ErrorMessage.MIN_SIZE));
    }

    @Test
    public void stateRequired() {
        Address address = SampleModel.sampleAddress();
        address.setState(null);

        EasyValidator.Result<Address> violations = new EasyValidator().validate(address);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void stateMinSize() {
        Address address = SampleModel.sampleAddress();
        address.setState("C"); // instead of CE or Ceará

        EasyValidator.Result<Address> violations = new EasyValidator().validate(address);

        assertTrue(violations.contains(ErrorMessage.MIN_SIZE));
    }

    @Test
    public void streetRequired() {
        Address address = SampleModel.sampleAddress();
        address.setStreet(null);

        EasyValidator.Result<Address> violations = new EasyValidator().validate(address);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }
}
