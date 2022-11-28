package br.com.srmourasilva.desafio.dto.auth;

public class CredentialsDTO {
    private final String username;
    private final String password;

    public CredentialsDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
