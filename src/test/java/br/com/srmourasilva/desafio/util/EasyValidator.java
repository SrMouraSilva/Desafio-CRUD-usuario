package br.com.srmourasilva.desafio.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

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
    }

    public <T> Result<T> validate(T entity) {
        return new Result<T>(validator.validate(entity));
    }
}
