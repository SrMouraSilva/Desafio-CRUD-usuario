package br.com.srmourasilva.desafio.test.model;

import br.com.srmourasilva.desafio.model.Address;
import br.com.srmourasilva.desafio.sample.ModelBuilder;
import br.com.srmourasilva.desafio.util.EasyValidator;
import br.com.srmourasilva.desafio.validation.ErrorMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressTest {

    @Test
    public void sampleAddressIsValid() {
        Address sampleAddress = ModelBuilder.sampleAddress();

        EasyValidator.Result<Address> violations = new EasyValidator().validate(sampleAddress);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void countryRequired() {
        Address address = ModelBuilder.sampleAddress();
        address.setCountry(null);

        EasyValidator.Result<Address> violations = new EasyValidator().validate(address);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void countryMinSize() {
        Address address = ModelBuilder.sampleAddress();
        address.setCountry("Per"); // instead of Peru

        EasyValidator.Result<Address> violations = new EasyValidator().validate(address);

        assertTrue(violations.contains(ErrorMessage.MIN_SIZE));
    }

    @Test
    public void stateRequired() {
        Address address = ModelBuilder.sampleAddress();
        address.setState(null);

        EasyValidator.Result<Address> violations = new EasyValidator().validate(address);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void stateMinSize() {
        Address address = ModelBuilder.sampleAddress();
        address.setState("C"); // instead of CE or Ceará

        EasyValidator.Result<Address> violations = new EasyValidator().validate(address);

        assertTrue(violations.contains(ErrorMessage.MIN_SIZE));
    }

    @Test
    public void streetRequired() {
        Address address = ModelBuilder.sampleAddress();
        address.setStreet(null);

        EasyValidator.Result<Address> violations = new EasyValidator().validate(address);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }
}
