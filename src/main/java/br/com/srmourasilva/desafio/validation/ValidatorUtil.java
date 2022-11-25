package br.com.srmourasilva.desafio.validation;

import br.com.srmourasilva.desafio.validation.regex.PasswordRegex;

import java.util.regex.Pattern;

import static br.com.srmourasilva.desafio.usecase.user.UserMessage.PASSWORD;

public class ValidatorUtil {
    public static Messages validatePassword(String password) {
        Messages messages = new Messages();
        boolean valid = Pattern.compile(PasswordRegex.PASSWORD).matcher(password).matches();

        if (!valid) {
            messages.add(PASSWORD, messages);
        }

        return messages;
    }
}
