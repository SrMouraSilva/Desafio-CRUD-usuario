package br.com.srmourasilva.desafio.test.usecase.user;

import br.com.srmourasilva.desafio.exception.NotFoundException;
import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.repository.UserRepository;
import br.com.srmourasilva.desafio.sample.SampleModel;
import br.com.srmourasilva.desafio.usecase.user.DeleteUserUseCase;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DeleteUserUseCaseTest {
    private final UserRepository repository = mock(UserRepository.class);
    private final DeleteUserUseCase useCase = new DeleteUserUseCase(repository);

    @Test
    public void deleteUser() {
        User anaCatarina = SampleModel.sampleUser();
        anaCatarina.setId(UUID.randomUUID().toString());
        anaCatarina.setFullName("Ana Catarina");
        anaCatarina.setEmail("ana.catarina@example.com");

        when(repository.findById(anaCatarina.getId())).thenReturn(Optional.of(anaCatarina));

        useCase.delete(anaCatarina.getId());

        verify(repository, times(1)).delete(anaCatarina);
    }


    @Test
    public void tryDeleteNonexistentUser() {
        User anaCatarina = SampleModel.sampleUser();
        anaCatarina.setId(UUID.randomUUID().toString());
        anaCatarina.setFullName("Ana Catarina");
        anaCatarina.setEmail("ana.catarina@example.com");

        when(repository.findById(anaCatarina.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.delete(anaCatarina.getId()));

        verify(repository, times(0)).delete(any());
    }
}
