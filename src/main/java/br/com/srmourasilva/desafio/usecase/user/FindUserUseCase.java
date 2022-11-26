package br.com.srmourasilva.desafio.usecase.user;

import br.com.srmourasilva.desafio.dto.user.UserResponseDTO;
import br.com.srmourasilva.desafio.repository.UserRepository;
import br.com.srmourasilva.desafio.usecase.user.filter.FindUserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;


public class FindUserUseCase {

    private final UserRepository repository;

    public FindUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public Page<UserResponseDTO> find(FindUserFilter filter, Pageable pageable) {
        Query query = filter.toQuery();
        return repository.findAll(query, pageable);
    }
}
