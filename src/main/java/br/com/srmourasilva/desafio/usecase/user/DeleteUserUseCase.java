package br.com.srmourasilva.desafio.usecase.user;

import br.com.srmourasilva.desafio.exception.NotFoundException;
import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.repository.UserRepository;

import java.util.Optional;


public class DeleteUserUseCase {

    private final UserRepository repository;

    public DeleteUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void delete(String id) {
        Optional<User> user = repository.findById(id);

        if (!user.isPresent()) {
            throw new NotFoundException(id);
        }

        repository.delete(user.get());
    }
}
