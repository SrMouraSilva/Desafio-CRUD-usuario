package br.com.srmourasilva.desafio.test.controller;

import br.com.srmourasilva.desafio.config.MongoContainerSetup;
import br.com.srmourasilva.desafio.dto.api.ApiError;
import br.com.srmourasilva.desafio.dto.user.UserResponseDTO;
import br.com.srmourasilva.desafio.dto.user.UserUpdateRequestDTO;
import br.com.srmourasilva.desafio.model.Profile;
import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.repository.UserRepository;
import br.com.srmourasilva.desafio.sample.SampleModel;
import br.com.srmourasilva.desafio.validation.mesage.UserMessage;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers={MongoContainerSetup.class})
@AutoConfigureWebTestClient
public class UserControllerTest {

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    private UserRepository repository;

    @AfterEach
    void cleanUp() {
        repository.deleteAll();
    }

    @Test
    public void createNewUser() {
        User user = SampleModel.sampleUser();

        user.setEmail(UUID.randomUUID()+"@gmail.com");

        UserResponseDTO userDTO = assertIsValidCreatedUser(createUser(user), user).returnResult().getResponseBody();

        deleteUser(userDTO);
    }

    @Test
    public void createNewUserWithSameEmail() {
        final String email = "duplicated@gmail.com";
        User user = SampleModel.sampleUser();
        user.setFullName("Test 1");
        user.setEmail(email);

        User otherUser = SampleModel.sampleUser();
        otherUser.setFullName("Test 2");
        otherUser.setEmail(email);

        assertNotEquals(user, otherUser);

        // First user
        assertIsValidCreatedUser(createUser(user), user);

        // new user with same email
        assertIsBadRequest(
            createUser(user),
            UserMessage.EMAIL_ALREADY_IN_USE
        );
    }

    @Test
    public void createNewUserWithWeakPassword() {
        User user = SampleModel.sampleUser();
        user.setFullName("Test 1");
        user.setPassword("weak-password");

        assertIsBadRequest(
            createUser(user),
            UserMessage.PASSWORD
        );
    }

    @Test
    public void findUsers() {
        User anaCatarina = SampleModel.sampleUser();
        anaCatarina.setFullName("Ana Catarina");
        anaCatarina.setEmail("ana.catarina@example.com");
        anaCatarina.setProfile(Profile.ADMIN);

        User irmaoDoJorel = SampleModel.sampleUser();
        irmaoDoJorel.setFullName("Irmão do Jorel");
        irmaoDoJorel.setEmail("juliano.enrico@example.com");
        irmaoDoJorel.setProfile(Profile.USER);

        UserResponseDTO anaCatarinaDTO = assertIsValidCreatedUser(createUser(anaCatarina), anaCatarina).returnResult().getResponseBody();
        UserResponseDTO irmaoDoJorelDTO = assertIsValidCreatedUser(createUser(irmaoDoJorel), irmaoDoJorel).returnResult().getResponseBody();

        webTestClient.get()
            .uri("/users?profile="+Profile.USER.name())
            .exchange()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.numberOfElements").isEqualTo(1)
            .jsonPath("$.content[0].id").isNotEmpty()
            .jsonPath("$.content[0].fullName").isEqualTo(irmaoDoJorel.getFullName())
        ;

        deleteUser(anaCatarinaDTO);
        deleteUser(irmaoDoJorelDTO);
    }

    @Test
    public void findUsersById() {
        User anaCatarina = SampleModel.sampleUser();
        anaCatarina.setFullName("Ana Catarina");
        anaCatarina.setEmail("ana.catarina@example.com");
        anaCatarina.setProfile(Profile.ADMIN);

        User irmaoDoJorel = SampleModel.sampleUser();
        irmaoDoJorel.setFullName("Irmão do Jorel");
        irmaoDoJorel.setEmail("juliano.enrico@example.com");
        irmaoDoJorel.setProfile(Profile.USER);

        UserResponseDTO anaCatarinaDTO = assertIsValidCreatedUser(createUser(anaCatarina), anaCatarina).returnResult().getResponseBody();
        UserResponseDTO irmaoDoJorelDTO = assertIsValidCreatedUser(createUser(irmaoDoJorel), irmaoDoJorel).returnResult().getResponseBody();

        assertIsUserFound(findUser(anaCatarinaDTO), anaCatarinaDTO);

        deleteUser(anaCatarinaDTO);
        deleteUser(irmaoDoJorelDTO);
    }

    private void assertIsUserFound(WebTestClient.@NotNull ResponseSpec spec, UserResponseDTO dto) {
        spec.expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectStatus().isOk()
            .expectBody()
            .jsonPath(".id").isNotEmpty()
            .equals(dto);
    }

    @NotNull
    private WebTestClient.ResponseSpec findUser(UserResponseDTO anaCatarinaDTO) {
        return webTestClient.get()
                .uri("/users/" + anaCatarinaDTO.getId())
                .exchange();
    }

    @Test
    public void deleteUser() {
        User anaCatarina = SampleModel.sampleUser();
        anaCatarina.setFullName("Ana Catarina");
        anaCatarina.setEmail("ana.catarina@example.com");
        anaCatarina.setProfile(Profile.ADMIN);

        UserResponseDTO anaCatarinaDTO = assertIsValidCreatedUser(createUser(anaCatarina), anaCatarina).returnResult().getResponseBody();

        assertIsUserFound(findUser(anaCatarinaDTO), anaCatarinaDTO);

        deleteUser(anaCatarinaDTO);

        findUser(anaCatarinaDTO)
            .expectStatus().isNotFound();
    }

    @Test
    public void updateUser() {
        User anaCatarina = SampleModel.sampleUser();
        anaCatarina.setFullName("Ana Catarina");
        anaCatarina.setEmail("ana.catarina.update_user@example.com");
        anaCatarina.setProfile(Profile.ADMIN);

        final String phone = "+55 0800 0000 0000";

        UserUpdateRequestDTO body = new UserUpdateRequestDTO();
        body.setPhone(JsonNullable.of(phone));

        UserResponseDTO anaCatarinaDTO = assertIsValidCreatedUser(createUser(anaCatarina), anaCatarina).returnResult().getResponseBody();

        assertNotEquals(phone, anaCatarina.getPhone());

        webTestClient.patch()
            .uri("/users/"+anaCatarinaDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(UserResponseDTO.class)
            .consumeWith( it -> {
                assertEquals(phone, it.getResponseBody().getPhone());
            });

        deleteUser(anaCatarinaDTO);
    }

    private WebTestClient.ResponseSpec createUser(User user) {
        return webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange();
    }

    private WebTestClient.ResponseSpec deleteUser(UserResponseDTO user) {
        return webTestClient.delete()
                .uri("/users/"+user.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    private WebTestClient.BodySpec<UserResponseDTO, ?> assertIsValidCreatedUser(WebTestClient.ResponseSpec spec, User user) {
        return spec
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(UserResponseDTO.class)
            .consumeWith( it -> {
                UserResponseDTO expectedUser = new UserResponseDTO(user);
                expectedUser.setId(it.getResponseBody().getId());

                assertNotNull(it.getResponseBody().getId());
                assertEquals(expectedUser, it.getResponseBody());
            });
    }

    private void assertIsBadRequest(WebTestClient.ResponseSpec spec, String message) {
        spec.expectStatus().isBadRequest()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(ApiError.class)
            .consumeWith( it -> {
                assertTrue(existsMessage(it, message));
            });
    }

    private static boolean existsMessage(EntityExchangeResult<ApiError> result, String message) {
        return result.getResponseBody().getDetails().stream().anyMatch(
            it -> it.getMessage().equals(message)
        );
    }
}
