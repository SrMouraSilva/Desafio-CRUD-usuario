package br.com.srmourasilva.desafio.controller;

import br.com.srmourasilva.desafio.dto.user.UserResponseDTO;
import br.com.srmourasilva.desafio.exception.NotFoundException;
import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.repository.UserRepository;
import br.com.srmourasilva.desafio.usecase.user.CreateUserUseCase;
import br.com.srmourasilva.desafio.usecase.user.DeleteUserUseCase;
import br.com.srmourasilva.desafio.usecase.user.FindUserUseCase;
import br.com.srmourasilva.desafio.usecase.user.filter.FindUserFilter;
import br.com.srmourasilva.desafio.usecase.user.password.HashGenerator;
import br.com.srmourasilva.desafio.util.PageableParameter;
import br.com.srmourasilva.desafio.validation.regex.PasswordRegex;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/users")
@Tag(name="Users", description="Information related to user")
@Validated
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private HashGenerator hash;

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
        CreateUserUseCase useCase = new CreateUserUseCase(repository, hash);

        return useCase.createUser(user);
    }

    //@Secured(value=Profile.Constant.USER)
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        security = {@SecurityRequirement(name = "Basic")},
        summary = "Find users",
        description = "Find users, according to the specified query parameters."
    )
    @PageableParameter
    @GetMapping(value="", produces={MediaType.APPLICATION_JSON_VALUE})
    public Page<UserResponseDTO> findUser(
        @ParameterObject FindUserFilter filter,
        @Parameter(hidden=true) Pageable pageable
    ) {
        FindUserUseCase useCase = new FindUserUseCase(repository);

        return useCase.find(filter, pageable);
    }

    //@Secured(value=Profile.Constant.USER)
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            security = {@SecurityRequirement(name = "Basic")},
            summary = "Get user information",
            description = "Get a user data by their unique identifier."
    )
    @GetMapping(value="/{id}", produces={MediaType.APPLICATION_JSON_VALUE})
    public UserResponseDTO findUserById(
            @PathVariable String id
    ) {
        FindUserFilter filter = new FindUserFilter();
        filter.setId(Lists.newArrayList(id));

        FindUserUseCase useCase = new FindUserUseCase(repository);

        Page<UserResponseDTO> response = useCase.find(filter, Pageable.unpaged());

        if (!response.hasContent()) {
            throw new NotFoundException(id);
        }

        return response.getContent().get(0);
    }

    //@Secured(value=Profile.Constant.ADMIN)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        security = {@SecurityRequirement(name = "Basic")},
        summary = "Delete a user",
        description = "Delete a user by it's id."
    )
    @DeleteMapping(value="/{id}", produces={MediaType.APPLICATION_JSON_VALUE})
    public void delete(
        @PathVariable String id
    ) {
        // FIXME - Verify that current user there trying to delete themself
        DeleteUserUseCase useCase = new DeleteUserUseCase(repository);

        useCase.delete(id);
    }
}
