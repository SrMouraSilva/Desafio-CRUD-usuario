package br.com.srmourasilva.desafio.usecase.user.password;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class Argon2HashGenerator implements HashGenerator {

    private final Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();

    @Override
    public String hash(String password) {
        return encoder.encode(password);
    }
}
