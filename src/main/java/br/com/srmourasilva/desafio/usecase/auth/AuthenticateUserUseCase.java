package br.com.srmourasilva.desafio.usecase.auth;

import br.com.srmourasilva.desafio.config.security.JwtService;
import br.com.srmourasilva.desafio.dto.auth.CredentialsDTO;
import br.com.srmourasilva.desafio.dto.auth.TokenResponseDTO;
import br.com.srmourasilva.desafio.exception.AuthenticationException;
import br.com.srmourasilva.desafio.exception.InvalidCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class AuthenticateUserUseCase {

    private final UserDetailsService service;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthenticateUserUseCase(UserDetailsService service, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public TokenResponseDTO authenticate(CredentialsDTO credentials) {
        UserDetails userDetails = getAuthenticatedUserBy(credentials)
                .orElseThrow(InvalidCredentialsException::new);

        return jwtService.newToken(userDetails);
    }

    public Optional<UserDetails> getAuthenticatedUserBy(CredentialsDTO credentials) {
        UserDetails user = findUser(credentials);

        if (passwordEncoder.matches(credentials.getPassword(), user.getPassword()))
            return Optional.of(user);
        else
            return Optional.empty();
    }

    private UserDetails findUser(CredentialsDTO credentials) {
        try {
            return service.loadUserByUsername(credentials.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new AuthenticationException(e.getMessage(), e);
        }
    }
}
