package br.com.srmourasilva.desafio.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EasyValidator {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static class Result<T> {

        private final Set<ConstraintViolation<T>> violations;

        public Result(Set<ConstraintViolation<T>> violations) {
            this.violations = violations;
        }

        public boolean contains(String message) {
            return violations.stream().anyMatch(it -> it.getMessage().equals(message));
        }

        public boolean isEmpty() {
            return violations.isEmpty();
        }

        public Messages toMessages() {
            List<Message> messages = violations.stream()
                .map(it -> new Message(it.getMessage(), it.getPropertyPath()))
                .collect(Collectors.toList());

            return new Messages(messages);
        }
    }

    public <T> Result<T> validate(T entity) {
        return new Result<T>(validator.validate(entity));
    }
}
