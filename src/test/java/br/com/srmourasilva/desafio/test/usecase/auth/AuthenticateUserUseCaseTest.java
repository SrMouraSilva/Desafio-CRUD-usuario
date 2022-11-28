package br.com.srmourasilva.desafio.test.usecase.auth;

import br.com.srmourasilva.desafio.config.security.JwtService;
import br.com.srmourasilva.desafio.config.security.UserDetailsImpl;
import br.com.srmourasilva.desafio.dto.auth.CredentialsDTO;
import br.com.srmourasilva.desafio.dto.auth.TokenResponseDTO;
import br.com.srmourasilva.desafio.exception.AuthenticationException;
import br.com.srmourasilva.desafio.exception.InvalidCredentialsException;
import br.com.srmourasilva.desafio.sample.SampleModel;
import br.com.srmourasilva.desafio.usecase.auth.AuthenticateUserUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticateUserUseCaseTest {

    private final UserDetailsService service = mock(UserDetailsService.class);

    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    private final JwtService jwtService = mock(JwtService.class);

    private final AuthenticateUserUseCase useCase = new AuthenticateUserUseCase(
        service,
        passwordEncoder,
        jwtService
    );

    @Test
    public void authenticate() {
        CredentialsDTO credentials = new CredentialsDTO("admin", "admin");
        UserDetails userDetails = new UserDetailsImpl(SampleModel.sampleUser());
        TokenResponseDTO token = new TokenResponseDTO("token", 0);

        when(service.loadUserByUsername(credentials.getUsername())).thenReturn(userDetails);
        when(passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword())).thenReturn(true);
        when(jwtService.newToken(userDetails)).thenReturn(token);

        TokenResponseDTO response = useCase.authenticate(credentials);

        assertEquals(token, response);
    }

    @Test
    public void authenticateWrongPassword() {
        CredentialsDTO credentials = new CredentialsDTO("admin", "admin");
        UserDetails userDetails = new UserDetailsImpl(SampleModel.sampleUser());

        when(service.loadUserByUsername(credentials.getUsername())).thenReturn(userDetails);
        when(passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> useCase.authenticate(credentials));
    }

    @Test
    public void getAuthenticatedUserByExistentUser() {
        CredentialsDTO credentials = new CredentialsDTO("admin", "admin");
        UserDetails userDetails = new UserDetailsImpl(SampleModel.sampleUser());

        when(service.loadUserByUsername(credentials.getUsername())).thenReturn(userDetails);
        when(passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword())).thenReturn(true);

        Optional<UserDetails> response = useCase.getAuthenticatedUserBy(credentials);

        assertTrue(response.isPresent());
        assertEquals(userDetails, response.get());
    }

    @Test
    public void getAuthenticatedUserByExistentUserButWrongPassword() {
        UserDetails userDetails = new UserDetailsImpl(SampleModel.sampleUser());
        CredentialsDTO credentials = new CredentialsDTO("admin", userDetails.getPassword());


        when(service.loadUserByUsername(credentials.getUsername())).thenReturn(userDetails);
        when(passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword())).thenReturn(false);

        Optional<UserDetails> response = useCase.getAuthenticatedUserBy(credentials);

        assertFalse(response.isPresent());
    }

    @Test
    public void getAuthenticatedUserByUnexistentUser() {
        CredentialsDTO credentials = new CredentialsDTO("admin", "admin");
        UserDetails userDetails = new UserDetailsImpl(SampleModel.sampleUser());

        when(service.loadUserByUsername(credentials.getUsername())).thenThrow(UsernameNotFoundException.class);

        assertThrows(AuthenticationException.class, () -> useCase.getAuthenticatedUserBy(credentials));
    }
}
