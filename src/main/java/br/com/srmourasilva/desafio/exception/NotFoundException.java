package br.com.srmourasilva.desafio.exception;

import br.com.srmourasilva.desafio.validation.mesage.ArchitectureMessage;
import br.com.srmourasilva.desafio.validation.Messages;

public class NotFoundException extends ArchitectureException {
    private final Messages messages;

    public NotFoundException(String id) {
        super();

        Messages messages = new Messages();
        messages.add(ArchitectureMessage.ENTITY_NOT_FOUND, id);

        this.messages = messages;
    }

    public Messages getMessages() {
        return messages;
    }
}
