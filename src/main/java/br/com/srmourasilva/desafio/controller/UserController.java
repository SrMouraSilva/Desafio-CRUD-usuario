package br.com.srmourasilva.desafio.controller;

import br.com.srmourasilva.desafio.dto.UserResponseDTO;
import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.validation.regex.PasswordRegex;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
@Tag(name="User", description="Information related to user")
@Validated
public class UserController {

    //@Secured(value=Profile.Constant.ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        security = {@SecurityRequirement(name = "Basic")},
        summary = "Create a new user",
        description = "Create a new user with email, password, and person information.\n\n" +
            "Password requirements:\n\n" +
            " * Mininum lenght: " + User.Constraints.PASSWORD_MIN_SIZE + "\n" +
            " * Contains at least one lower alphabet character;\n" +
            " * Contains at least one upper alphabet charracter;\n" +
            " * Contains at least one digit;\n" +
            " * Contains at least one of the following special characters `"+ PasswordRegex.SPECIAL_CHARACTER +"`\n"
    )
    @PostMapping(value="", produces={MediaType.APPLICATION_JSON_VALUE}, consumes={MediaType.APPLICATION_JSON_VALUE})
    public UserResponseDTO createUser(
        @RequestBody @Valid User user
    ) {
        //CreateUserUseCase()
        return new UserResponseDTO();
    }
}
