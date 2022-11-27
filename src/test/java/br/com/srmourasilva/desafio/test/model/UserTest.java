package br.com.srmourasilva.desafio.test.model;

import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.sample.SampleModel;
import br.com.srmourasilva.desafio.validation.EasyValidator;
import br.com.srmourasilva.desafio.validation.mesage.ErrorMessage;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void sampleUserIsValid() {
        User sampleUser = SampleModel.sampleUser();

        EasyValidator.Result<User> violations = new EasyValidator().validate(sampleUser);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void equals() {
        User sampleUser = SampleModel.sampleUser();
        User sampleUserOtherInstance = SampleModel.sampleUser();
        User sampleUserWithDifferentValue = SampleModel.sampleUser();

        sampleUserWithDifferentValue.setFullName(UUID.randomUUID().toString());

        assertEquals(sampleUser, sampleUser);
        assertEquals(sampleUser, sampleUserOtherInstance);

        assertNotEquals(sampleUser, sampleUserWithDifferentValue);
    }

    @Test
    public void constructorWithCopy() {
        User sampleUser = SampleModel.sampleUser();
        User sampleUserDeepCopy = SampleModel.sampleUser();

        assertEquals(sampleUser, sampleUserDeepCopy);
        assertNotSame(sampleUser, sampleUserDeepCopy);
    }

    @Test
    public void fullNameRequired() {
        User user = SampleModel.sampleUser();
        user.setFullName(null);

        EasyValidator.Result<User> violations = new EasyValidator().validate(user);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void fullNameMinSize() {
        User user = SampleModel.sampleUser();
        user.setFullName("oi");

        EasyValidator.Result<User> violations = new EasyValidator().validate(user);

        assertTrue(violations.contains(ErrorMessage.MIN_SIZE));
    }

    @Test
    public void emailRequired() {
        User user = SampleModel.sampleUser();
        user.setEmail(null);

        EasyValidator.Result<User> violations = new EasyValidator().validate(user);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void emailValid() {
        User user = SampleModel.sampleUser();
        user.setEmail("email invalid@test.com");

        EasyValidator.Result<User> violations = new EasyValidator().validate(user);

        assertTrue(violations.contains(ErrorMessage.EMAIL));
    }

    @Test
    public void passwordRequired() {
        User user = SampleModel.sampleUser();
        user.setPassword(null);

        EasyValidator.Result<User> violations = new EasyValidator().validate(user);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void passwordMinSize() {
        User userWithShortName = SampleModel.sampleUser();
        userWithShortName.setPassword("12345");

        EasyValidator.Result<User> violations = new EasyValidator().validate(userWithShortName);

        assertTrue(violations.contains(ErrorMessage.MIN_SIZE));
    }

    @Test
    public void phoneOptional() {
        User userWithShortName = SampleModel.sampleUser();
        userWithShortName.setPhone(null);

        EasyValidator.Result<User> violations = new EasyValidator().validate(userWithShortName);

        assertFalse(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void phoneMinSize() {
        User userWithShortName = SampleModel.sampleUser();
        userWithShortName.setPhone("123");

        EasyValidator.Result<User> violations = new EasyValidator().validate(userWithShortName);

        assertTrue(violations.contains(ErrorMessage.MIN_SIZE));
    }

    @Test
    public void addressRequired() {
        User userWithShortName = SampleModel.sampleUser();
        userWithShortName.setAddress(null);

        EasyValidator.Result<User> violations = new EasyValidator().validate(userWithShortName);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }

    @Test
    public void profileRequired() {
        User userWithShortName = SampleModel.sampleUser();
        userWithShortName.setProfile(null);

        EasyValidator.Result<User> violations = new EasyValidator().validate(userWithShortName);

        assertTrue(violations.contains(ErrorMessage.REQUIRED));
    }
}
