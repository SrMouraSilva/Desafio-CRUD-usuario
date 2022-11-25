package br.com.srmourasilva.desafio.test.usecase.user;

import br.com.srmourasilva.desafio.exception.ValidationException;
import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.repository.UserRepository;
import br.com.srmourasilva.desafio.sample.SampleModel;
import br.com.srmourasilva.desafio.usecase.user.CreateUserUseCase;
import br.com.srmourasilva.desafio.usecase.user.password.HashGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CreateUserUseCaseTest {
    private final UserRepository repository = mock(UserRepository.class);
    private final HashGenerator hashGenerator = mock(HashGenerator.class);
    private final CreateUserUseCase useCase = new CreateUserUseCase(
        repository,
        hashGenerator
    );

    @Test
    public void createValidUser() {
        User user = SampleModel.sampleUser();
        final String hashPassword = "#hash#password";

        when(repository.existsByEmail(user.getEmail())).thenReturn(false);
        when(hashGenerator.hash(user.getPassword())).thenReturn(hashPassword);

        User userWithHashPassword = new User(user);
        userWithHashPassword.setPassword(hashGenerator.hash(user.getPassword()));

        useCase.createUser(user);

        verify(repository, times(1)).save(userWithHashPassword);
    }

    @Test
    public void userWithAnAlreadyUsedEmail() {
        User user = SampleModel.sampleUser();

        when(repository.existsByEmail(user.getEmail())).thenReturn(true);

        User userWithHashPassword = new User(user);
        userWithHashPassword.setPassword(hashGenerator.hash(user.getPassword()));

        assertThrows(ValidationException.class, () -> useCase.createUser(user));
    }
}
