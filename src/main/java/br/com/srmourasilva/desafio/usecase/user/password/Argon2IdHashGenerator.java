package br.com.srmourasilva.desafio.usecase.user.password;

import de.mkammerer.argon2.Argon2Factory;

public class Argon2IdHashGenerator implements HashGenerator {

    /**
     * Converts a password into argon2id pattern, following
     * <a href="https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html#introduction">OWASP recommendations</a>
     */
    @Override
    public String hash(String password) {
        return Argon2Factory.create()
                .hash(4, 15 * 1024, 1, password.toCharArray());
    }
}
