package br.com.srmourasilva.desafio.config;

import br.com.srmourasilva.desafio.dto.api.ApiError;
import br.com.srmourasilva.desafio.exception.ValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@Order(Ordered.HIGHEST_PRECEDENCE+1)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handle(ValidationException ex) {
        ApiError error = new ApiError(
            HttpStatus.BAD_REQUEST,
            "Validation error",
            ex.getMessages().getMessages()
        );

        return dispatch(error);
    }

    private ResponseEntity<Object> dispatch(ApiError apiError) {
        return new ResponseEntity(apiError, apiError.getStatus());
    }
}
