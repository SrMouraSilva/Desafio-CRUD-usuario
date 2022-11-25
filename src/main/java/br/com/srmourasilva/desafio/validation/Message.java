package br.com.srmourasilva.desafio.validation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Message {
    private final String message;
    private final List<Object> context;

    public Message(String message) {
        this(message, new LinkedList<>());
    }

    public Message(String message, Object... context) {
        this.message = message;
        this.context = Arrays.asList(context);
    }

    public Message(String message, List<Object> context) {
        this.message = message;
        this.context = context;
    }

    public String getMessage() {
        return message;
    }

    public List<Object> getContext() {
        return context;
    }
}
