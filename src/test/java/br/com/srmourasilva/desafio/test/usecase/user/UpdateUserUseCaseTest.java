package br.com.srmourasilva.desafio.test.usecase.user;

import br.com.srmourasilva.desafio.dto.user.UserResponseDTO;
import br.com.srmourasilva.desafio.dto.user.UserUpdateRequestDTO;
import br.com.srmourasilva.desafio.exception.NotFoundException;
import br.com.srmourasilva.desafio.exception.ValidationException;
import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.repository.UserRepository;
import br.com.srmourasilva.desafio.sample.SampleModel;
import br.com.srmourasilva.desafio.usecase.user.UpdateUserUseCase;
import br.com.srmourasilva.desafio.usecase.user.password.HashGenerator;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UpdateUserUseCaseTest {
    private final UserRepository repository = mock(UserRepository.class);
    private final HashGenerator hashGenerator = mock(HashGenerator.class);

    private final UpdateUserUseCase useCase = new UpdateUserUseCase(repository, hashGenerator);

    @Test
    public void updateUnexistentUser() {
        final String id = "1234";

        UserUpdateRequestDTO request = new UserUpdateRequestDTO();
        request.setFullName(JsonNullable.of("Rachel de Queiroz Sandy"));

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.update("1234", request));

        verify(repository, times(1)).findById(id);
        verify(repository, times(0)).save(any());
    }

    @Test
    public void updateUserWithNonUniqueEmail() {
        final String id = "1234";
        final String email = "1234@example.com";

        UserUpdateRequestDTO request = new UserUpdateRequestDTO();
        request.setEmail(JsonNullable.of(email));

        User sampleUser = SampleModel.sampleUser();

        when(repository.findById(id)).thenReturn(Optional.of(sampleUser));
        when(repository.existsByEmail(email)).thenReturn(true);

        assertThrows(ValidationException.class, () -> useCase.update("1234", request));

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).existsByEmail(email);
        verify(repository, times(0)).save(any());
    }

    @Test
    public void updateUserWithInvalidAttributeValue() {
        final String id = "1234";
        final String weakPassword = "1234";

        UserUpdateRequestDTO request = new UserUpdateRequestDTO();
        request.setPassword(JsonNullable.of(weakPassword));

        User sampleUser = SampleModel.sampleUser();

        when(repository.findById(id)).thenReturn(Optional.of(sampleUser));

        assertThrows(ValidationException.class, () -> useCase.update("1234", request));

        verify(repository, times(1)).findById(id);
        verify(repository, times(0)).save(any());
    }

    @Test
    public void updateUser() {
        final String id = "1234";
        final String phone = "+55 0800 0000 0000";

        UserUpdateRequestDTO request = new UserUpdateRequestDTO();
        request.setPhone(JsonNullable.of(phone));

        User sampleUser = SampleModel.sampleUser();

        User expected = request.join(sampleUser);

        when(repository.findById(id)).thenReturn(Optional.of(sampleUser));
        when(repository.save(expected)).thenReturn(expected);

        UserResponseDTO userUpdated = useCase.update("1234", request);

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(expected);
        assertEquals(new UserResponseDTO(expected), userUpdated);
    }
}
