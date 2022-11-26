package br.com.srmourasilva.desafio.test.validation;


import br.com.srmourasilva.desafio.validation.ValidatorUtil;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorUtilTest {
    @Test
    public void validatePassword() {
        String valid = "1aA!";

        String missingNumber = "aA!";
        String missingSmallerChar = "1A!";
        String missingUpperChar = "1a!";
        String missingSpecialChar = "1aA";

        String notVerifiedSpecialChar = "1aA¬";

        assertTrue(ValidatorUtil.validatePassword(valid).isEmpty());
        // Expected not include the password into error
        assertFalse(
            ValidatorUtil.validatePassword(valid).getMessages()
                .stream().map(it -> it.getContext().stream()).collect(Collectors.toList())
                .stream().anyMatch(it -> it.equals(valid))
        );

        assertFalse(ValidatorUtil.validatePassword(missingNumber).isEmpty());
        assertFalse(ValidatorUtil.validatePassword(missingSmallerChar).isEmpty());
        assertFalse(ValidatorUtil.validatePassword(missingUpperChar).isEmpty());
        assertFalse(ValidatorUtil.validatePassword(missingSpecialChar).isEmpty());

        assertFalse(ValidatorUtil.validatePassword(notVerifiedSpecialChar).isEmpty());
    }
}
