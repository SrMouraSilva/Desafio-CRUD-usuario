package br.com.srmourasilva.desafio.dto.api;

import br.com.srmourasilva.desafio.validation.Message;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

public class ApiError {
    private final HttpStatus status;

    private final String message;

    private final List<Message> details;

    private final OffsetDateTime timestamp = OffsetDateTime.now();

    public ApiError(HttpStatus status, String message, List<Message> details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Message> getDetails() {
        return details;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }
}
