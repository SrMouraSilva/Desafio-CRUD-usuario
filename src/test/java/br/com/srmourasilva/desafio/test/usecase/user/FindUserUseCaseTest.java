package br.com.srmourasilva.desafio.test.usecase.user;

import br.com.srmourasilva.desafio.dto.user.UserResponseDTO;
import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.repository.UserRepository;
import br.com.srmourasilva.desafio.sample.SampleModel;
import br.com.srmourasilva.desafio.usecase.user.FindUserUseCase;
import br.com.srmourasilva.desafio.usecase.user.filter.FindUserFilter;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FindUserUseCaseTest {
    private final UserRepository repository = mock(UserRepository.class);
    private final FindUserUseCase useCase = new FindUserUseCase(repository);

    @Test
    public void findAll() {
        User anaCatarina = SampleModel.sampleUser();
        anaCatarina.setId(UUID.randomUUID().toString());
        anaCatarina.setFullName("Ana Catarina");
        anaCatarina.setEmail("ana.catarina@example.com");

        User irmaoDoJorel = SampleModel.sampleUser();
        irmaoDoJorel.setId(UUID.randomUUID().toString());
        irmaoDoJorel.setFullName("Irmão do Jorel");
        irmaoDoJorel.setEmail("juliano.enrico@example.com");

        List<UserResponseDTO> users = Lists.list(
            new UserResponseDTO(anaCatarina),
            new UserResponseDTO(irmaoDoJorel)
        );

        Pageable pageable = Pageable.unpaged();

        FindUserFilter filter = new FindUserFilter();

        when(repository.findAll(any(Query.class), eq(pageable))).thenReturn(new PageImpl<>(users, pageable, users.size()));

        Page<UserResponseDTO> response = useCase.find(filter, pageable);

        verify(repository, times(1)).findAll(any(Query.class), eq(pageable));
        assertEquals(users, response.getContent());
    }
}
