package br.com.srmourasilva.desafio.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponseDTO {

    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("expires_in")
    private final int expiresIn;

    public TokenResponseDTO(String accessToken, int expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }
}
