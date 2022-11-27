package br.com.srmourasilva.desafio.usecase.user;

import br.com.srmourasilva.desafio.dto.user.UserResponseDTO;
import br.com.srmourasilva.desafio.dto.user.UserUpdateRequestDTO;
import br.com.srmourasilva.desafio.exception.NotFoundException;
import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.repository.UserRepository;
import br.com.srmourasilva.desafio.usecase.user.password.HashGenerator;
import br.com.srmourasilva.desafio.validation.EasyValidator;
import br.com.srmourasilva.desafio.validation.Messages;
import br.com.srmourasilva.desafio.validation.ValidatorUtil;

import java.util.Optional;

import static br.com.srmourasilva.desafio.validation.mesage.UserMessage.EMAIL_ALREADY_IN_USE;


public class UpdateUserUseCase {

    private final UserRepository repository;

    private final HashGenerator hash;

    public UpdateUserUseCase(UserRepository repository, HashGenerator hash) {
        this.repository = repository;
        this.hash = hash;
    }

    public UserResponseDTO update(String id, UserUpdateRequestDTO user) {
        User databaseUser = getUser(id);

        User userWithNewValues = user.join(databaseUser);

        Messages messages = validate(userWithNewValues, user);
        messages.throwIfNotEmpty();

        if (user.getPassword().isPresent()) {
            userWithNewValues.setPassword(hash.hash(user.getPassword().get()));
        }

        User userUpdated = repository.save(userWithNewValues);
        return new UserResponseDTO(userUpdated);
    }

    private User getUser(String id) {
        Optional<User> databaseUser = repository.findById(id);

        if (!databaseUser.isPresent()) {
            throw new NotFoundException(id);
        }

        return databaseUser.get();
    }

    private Messages validate(User user, UserUpdateRequestDTO request) {
        Messages messages = new EasyValidator()
                .validate(user)
                .toMessages();

        if (request.getEmail().isPresent() && repository.existsByEmail(user.getEmail())) {
            messages.add(EMAIL_ALREADY_IN_USE, user.getEmail());
        }

        if (request.getPassword().isPresent()) {
            messages.addAll(ValidatorUtil.validatePassword(user.getPassword()));
        }

        return messages;
    }
}
