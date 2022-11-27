package br.com.srmourasilva.desafio.exception;

public class TokenException extends AuthenticationException {

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
