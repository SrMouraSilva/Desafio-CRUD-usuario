package br.com.srmourasilva.desafio.test.model;

import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.sample.ModelBuilder;
import br.com.srmourasilva.desafio.util.EasyValidator;
import br.com.srmourasilva.desafio.validation.ErrorMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    @Test
    public void sampleUserIsValid() {
        User sampleUser = ModelBuilder.sampleUser();

        EasyValidator.Result<User> violations = new EasyValidator().validate(sampleUser);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void fullNameRequired() {
        User user = ModelBuilder.sampleUser();
        user.setFullName(null);

        EasyValidator.Result<User> violations = new EasyValidator().validate(user);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void fullNameMinSize() {
        User user = ModelBuilder.sampleUser();
        user.setFullName("oi");

        EasyValidator.Result<User> violations = new EasyValidator().validate(user);

        assertTrue(violations.contains(ErrorMessage.MIN_SIZE));
    }

    @Test
    public void emailRequired() {
        User user = ModelBuilder.sampleUser();
        user.setEmail(null);

        EasyValidator.Result<User> violations = new EasyValidator().validate(user);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void emailValid() {
        User user = ModelBuilder.sampleUser();
        user.setEmail("email invalid@test.com");

        EasyValidator.Result<User> violations = new EasyValidator().validate(user);

        assertTrue(violations.contains(ErrorMessage.EMAIL));
    }

    @Test
    public void passwordRequired() {
        User user = ModelBuilder.sampleUser();
        user.setPassword(null);

        EasyValidator.Result<User> violations = new EasyValidator().validate(user);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void passwordMinSize() {
        User userWithShortName = ModelBuilder.sampleUser();
        userWithShortName.setPassword("12345");

        EasyValidator.Result<User> violations = new EasyValidator().validate(userWithShortName);

        assertTrue(violations.contains(ErrorMessage.MIN_SIZE));
    }

    @Test
    public void phoneOptional() {
        User userWithShortName = ModelBuilder.sampleUser();
        userWithShortName.setPhone(null);

        EasyValidator.Result<User> violations = new EasyValidator().validate(userWithShortName);

        assertFalse(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void phoneMinSize() {
        User userWithShortName = ModelBuilder.sampleUser();
        userWithShortName.setPhone("123");

        EasyValidator.Result<User> violations = new EasyValidator().validate(userWithShortName);

        assertTrue(violations.contains(ErrorMessage.MIN_SIZE));
    }

    @Test
    public void addressRequired() {
        User userWithShortName = ModelBuilder.sampleUser();
        userWithShortName.setAddress(null);

        EasyValidator.Result<User> violations = new EasyValidator().validate(userWithShortName);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void profileRequired() {
        User userWithShortName = ModelBuilder.sampleUser();
        userWithShortName.setProfile(null);

        EasyValidator.Result<User> violations = new EasyValidator().validate(userWithShortName);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }
}
