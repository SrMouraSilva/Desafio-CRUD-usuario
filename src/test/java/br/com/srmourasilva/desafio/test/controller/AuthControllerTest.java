package br.com.srmourasilva.desafio.test.controller;

import br.com.srmourasilva.desafio.config.MongoContainerSetup;
import br.com.srmourasilva.desafio.dto.auth.CredentialsDTO;
import br.com.srmourasilva.desafio.model.Profile;
import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.repository.UserRepository;
import br.com.srmourasilva.desafio.sample.SampleModel;
import br.com.srmourasilva.desafio.usecase.user.password.Argon2HashGenerator;
import br.com.srmourasilva.desafio.usecase.user.password.HashGenerator;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers={MongoContainerSetup.class})
@AutoConfigureWebTestClient
public class AuthControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository repository;

    private final HashGenerator hashGenerator = new Argon2HashGenerator();

    private final User user = SampleModel.sampleAuthUser(Profile.USER);
    private final User admin = SampleModel.sampleAuthUser(Profile.ADMIN);

    private final String password = "secret";

    @BeforeEach
    void setUp() {
        user.setPassword(hashGenerator.hash(password));
        admin.setPassword(hashGenerator.hash(password));

        repository.save(user);
        repository.save(admin);
    }

    @Test
    public void authenticateValidUser() {
        CredentialsDTO credentials = new CredentialsDTO(user.getEmail(), password);

        authenticate(credentials)
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.access_token").isNotEmpty()
            .jsonPath("$.expires_in").isNotEmpty();
    }


    @Test
    public void authenticateInvalidUser() {
        String wrongPassword = hashGenerator.hash("wrong password");
        CredentialsDTO credentials = new CredentialsDTO(user.getEmail(), wrongPassword);

        authenticate(credentials)
            .expectStatus().isUnauthorized();
    }

    @Test
    public void authenticateNonexixtentUser() {
        String wrongPassword = hashGenerator.hash("wrong password");
        CredentialsDTO credentials = new CredentialsDTO("wrong.email@gmail.com", wrongPassword);

        authenticate(credentials)
                .expectStatus().isUnauthorized();
    }

    @NotNull
    private WebTestClient.ResponseSpec authenticate(CredentialsDTO credentials) {
        return webTestClient.post()
                .uri("/auth?username=" + credentials.getUsername() + "&password=" + credentials.getPassword())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }
}
