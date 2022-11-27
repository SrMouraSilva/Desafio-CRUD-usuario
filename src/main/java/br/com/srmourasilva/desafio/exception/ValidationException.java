package br.com.srmourasilva.desafio.exception;

import br.com.srmourasilva.desafio.validation.Messages;

public class ValidationException extends ArchitectureException {
    private final Messages messages;

    public ValidationException(Messages messages) {
        super();
        this.messages = messages;
    }

    public Messages getMessages() {
        return messages;
    }
}
