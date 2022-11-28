package br.com.srmourasilva.desafio.config;

import br.com.srmourasilva.desafio.dto.api.ApiErrorDTO;
import br.com.srmourasilva.desafio.exception.AuthenticationException;
import br.com.srmourasilva.desafio.exception.NotFoundException;
import br.com.srmourasilva.desafio.exception.ValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedList;


@Order(Ordered.HIGHEST_PRECEDENCE+1)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handle(NotFoundException ex) {
        ApiErrorDTO error = new ApiErrorDTO(
            HttpStatus.NOT_FOUND,
            "Validation error",
            ex.getMessages().getMessages()
        );

        return dispatch(error);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handle(ValidationException ex) {
        ApiErrorDTO error = new ApiErrorDTO(
            HttpStatus.BAD_REQUEST,
            "Validation error",
            ex.getMessages().getMessages()
        );

        return dispatch(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handle(AuthenticationException ex) {
        ApiErrorDTO error = new ApiErrorDTO(
            HttpStatus.UNAUTHORIZED,
            ex.getMessage(),
            new LinkedList<>()
        );

        return dispatch(error);
    }

    private ResponseEntity<Object> dispatch(ApiErrorDTO apiError) {
        return new ResponseEntity(apiError, apiError.getStatus());
    }
}
