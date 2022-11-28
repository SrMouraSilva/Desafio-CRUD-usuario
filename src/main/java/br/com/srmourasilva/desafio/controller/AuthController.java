package br.com.srmourasilva.desafio.controller;

import br.com.srmourasilva.desafio.config.security.JwtService;
import br.com.srmourasilva.desafio.dto.auth.CredentialsDTO;
import br.com.srmourasilva.desafio.dto.auth.TokenResponseDTO;
import br.com.srmourasilva.desafio.usecase.auth.AuthenticateUserUseCase;
import br.com.srmourasilva.desafio.validation.mesage.ArchitectureMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name="Authentication", description="Users authentication")
public class AuthController {
    @Autowired
    private UserDetailsService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @ResponseStatus(HttpStatus.OK)
    @Operation(
        security = {@SecurityRequirement(name = "Basic")},
        summary = "Authenticate",
        description = "Obtains a token based on informed credentials. \n\n" +
            "If you want change the current password, please check [PATCH `/users/{id}`](#tag/Users/operation/patch).",
        responses = {
            @ApiResponse(responseCode = "200", description = "User successfully authenticated"),
            @ApiResponse(responseCode = "401", description = ArchitectureMessage.INVALID_CREDENTIALS)
        }
    )
    @PostMapping(value="", consumes={MediaType.APPLICATION_FORM_URLENCODED_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
    public TokenResponseDTO authenticate(
        @Schema(description="User's email", example="rachel.queiroz@gmail.com")
        String username,
        @Schema(description="User's password", example="S3cretP@ssword")
        String password
    ) {
        AuthenticateUserUseCase useCase = new AuthenticateUserUseCase(service, passwordEncoder, jwtService);

        return useCase.authenticate(new CredentialsDTO(username, password));
    }
}