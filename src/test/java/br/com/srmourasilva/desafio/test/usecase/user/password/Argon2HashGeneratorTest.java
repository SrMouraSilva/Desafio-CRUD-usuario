package br.com.srmourasilva.desafio.test.usecase.user.password;

import br.com.srmourasilva.desafio.usecase.user.password.Argon2HashGenerator;
import org.junit.jupiter.api.Test;

public class Argon2HashGeneratorTest {

    /**
     * Breaks if bouncy cassle there isn't present
     */
    @Test
    public void testArgon2DependenciesExists() {
        new Argon2HashGenerator().hash("test");
    }
}
