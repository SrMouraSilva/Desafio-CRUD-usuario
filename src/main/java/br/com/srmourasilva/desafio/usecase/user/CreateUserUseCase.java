package br.com.srmourasilva.desafio.usecase.user;

import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.usecase.user.password.HashGenerator;
import br.com.srmourasilva.desafio.repository.UserRepository;
import br.com.srmourasilva.desafio.validation.EasyValidator;
import br.com.srmourasilva.desafio.validation.Messages;
import br.com.srmourasilva.desafio.validation.ValidatorUtil;

import static br.com.srmourasilva.desafio.usecase.user.UserMessage.EMAIL_ALREADY_IN_USE;

public class CreateUserUseCase {

    private final UserRepository repository;
    private final HashGenerator hash;

    public CreateUserUseCase(UserRepository repository, HashGenerator hash) {
        this.repository = repository;
        this.hash = hash;
    }

    public User createUser(User user) {
        Messages messages = validate(user);
        messages.throwIfNotEmpty();

        final User userWithHashPassoword = generateUserWithHashPassword(user);

        return repository.save(userWithHashPassoword);
    }

    private User generateUserWithHashPassword(User user) {
        final String hashPassword = hash.hash(user.getPassword());

        final User userWithHashPassoword = new User(user);
        userWithHashPassoword.setPassword(hashPassword);

        return userWithHashPassoword;
    }

    public Messages validate(User user) {
        Messages messages = new EasyValidator()
            .validate(user)
            .toMessages();

        if (repository.existsByEmail(user.getEmail())) {
            messages.add(EMAIL_ALREADY_IN_USE, user.getEmail());
        }

        messages.addAll(ValidatorUtil.validatePassword(user.getPassword()));

        return messages;
    }
}
