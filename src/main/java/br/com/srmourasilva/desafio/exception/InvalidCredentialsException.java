package br.com.srmourasilva.desafio.exception;

import br.com.srmourasilva.desafio.validation.mesage.ArchitectureMessage;

public class InvalidCredentialsException extends AuthenticationException {

    public InvalidCredentialsException() {
        super(ArchitectureMessage.INVALID_CREDENTIALS);
    }

}
