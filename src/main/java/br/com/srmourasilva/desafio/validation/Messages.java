package br.com.srmourasilva.desafio.validation;

import br.com.srmourasilva.desafio.exception.ValidationException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Messages {
    private final List<Message> messages = new LinkedList<>();

    public Messages() {}

    public Messages(List<Message> messages) {
        this();

        this.messages.addAll(messages);
    }

    public void add(String message, Object... context) {
        messages.add(
            new Message(message, Arrays.asList(context))
        );
    }

    public void throwIfNotEmpty() {
        if (!messages.isEmpty()) {
            throw new ValidationException(this);
        }
    }

    public void addAll(Messages messages) {
        this.messages.addAll(messages.messages);
    }

    public boolean isEmpty() {
        return this.messages.isEmpty();
    }

    public List<Message> getMessages() {
        return messages;
    }
}
