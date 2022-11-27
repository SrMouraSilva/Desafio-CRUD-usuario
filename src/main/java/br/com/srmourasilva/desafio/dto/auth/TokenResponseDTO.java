package br.com.srmourasilva.desafio.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponseDTO {

    @JsonProperty("id_token")
    private final String idToken;

    @JsonProperty("expires_in")
    private final int expiresIn;

    public TokenResponseDTO(String idToken, int expiresIn) {
        this.idToken = idToken;
        this.expiresIn = expiresIn;
    }

    public String getIdToken() {
        return idToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }
}
