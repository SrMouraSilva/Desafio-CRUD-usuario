package br.com.srmourasilva.desafio.controller;

import br.com.srmourasilva.desafio.dto.api.ApiErrorDTO;
import br.com.srmourasilva.desafio.dto.user.UserResponseDTO;
import br.com.srmourasilva.desafio.dto.user.UserUpdateRequestDTO;
import br.com.srmourasilva.desafio.exception.NotFoundException;
import br.com.srmourasilva.desafio.model.Profile;
import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.repository.UserRepository;
import br.com.srmourasilva.desafio.usecase.user.CreateUserUseCase;
import br.com.srmourasilva.desafio.usecase.user.DeleteUserUseCase;
import br.com.srmourasilva.desafio.usecase.user.FindUserUseCase;
import br.com.srmourasilva.desafio.usecase.user.UpdateUserUseCase;
import br.com.srmourasilva.desafio.usecase.user.filter.FindUserFilter;
import br.com.srmourasilva.desafio.usecase.user.password.HashGenerator;
import br.com.srmourasilva.desafio.util.PageableParameter;
import br.com.srmourasilva.desafio.validation.mesage.ArchitectureMessage;
import br.com.srmourasilva.desafio.validation.regex.PasswordRegex;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static br.com.srmourasilva.desafio.validation.mesage.UserMessage.EMAIL_ALREADY_IN_USE;
import static br.com.srmourasilva.desafio.validation.mesage.UserMessage.PASSWORD;


@RestController
@RequestMapping("/users")
@Tag(name="Users", description="Information related to user")
@Validated
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private HashGenerator hash;

    @Secured("ROLE_"+Profile.Constant.ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        security = {@SecurityRequirement(name = "OIDC")},
        summary = "Create a new user",
            description = "**Requires `"+Profile.Constant.ADMIN+"` profile**.\n\n" +
            "Create a new user with email, password, and person information.\n\n" +
            "Password requirements:\n\n" +
            " * Mininum lenght: " + User.Constraints.PASSWORD_MIN_SIZE + "\n" +
            " * Contains at least one lower alphabet character;\n" +
            " * Contains at least one upper alphabet charracter;\n" +
            " * Contains at least one digit;\n" +
            " * Contains at least one of the following special characters `"+ PasswordRegex.SPECIAL_CHARACTER +"`\n",
        responses = {
            @ApiResponse(responseCode = "201", description = "New user successfully created"),
            @ApiResponse(responseCode = "400", description = EMAIL_ALREADY_IN_USE + ", " + PASSWORD, content={@Content(schema=@Schema(implementation=ApiErrorDTO.class))})
        }
    )
    @PostMapping(value="", produces={MediaType.APPLICATION_JSON_VALUE}, consumes={MediaType.APPLICATION_JSON_VALUE})
    public UserResponseDTO createUser(
        @RequestBody @Valid User user
    ) {
        CreateUserUseCase useCase = new CreateUserUseCase(repository, hash);

        return useCase.createUser(user);
    }

    @Secured(value={"ROLE_"+Profile.Constant.ADMIN, "ROLE_"+Profile.Constant.USER})
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        security = {@SecurityRequirement(name = "OIDC")},
        summary = "Find users",
        description = "**Requires `"+Profile.Constant.USER+"` or `"+Profile.Constant.ADMIN+"` profile**.\n\n" +
            "Find users, according to the specified query parameters.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Users found")
        }
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

    @Secured(value={"ROLE_"+Profile.Constant.ADMIN, "ROLE_"+Profile.Constant.USER})
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        security = {@SecurityRequirement(name = "OIDC")},
        summary = "Get user information",
        description = "**Requires `"+Profile.Constant.USER+"` or `"+Profile.Constant.ADMIN+"` profile**.\n\n" +
            "Get a user data by their unique identifier.",
        responses = {
            @ApiResponse(responseCode = "201", description = "New user successfully created"),
            @ApiResponse(responseCode = "404", description = ArchitectureMessage.ENTITY_NOT_FOUND, content={@Content(schema=@Schema(implementation=ApiErrorDTO.class))})
        }
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

    @Secured(value="ROLE_"+Profile.Constant.ADMIN)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        security = {@SecurityRequirement(name = "OIDC")},
        summary = "Delete a user",
        description = "**Requires `"+Profile.Constant.ADMIN+"` profile**.\n\n" +
            "Delete a user by their unique identifier.",
        responses = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted"),
            @ApiResponse(responseCode = "404", description = ArchitectureMessage.ENTITY_NOT_FOUND, content={@Content(schema=@Schema(implementation=ApiErrorDTO.class))})
        }
    )
    @DeleteMapping(value="/{id}", produces={MediaType.APPLICATION_JSON_VALUE})
    public void delete(
        @PathVariable String id
    ) {
        // FIXME - Verify if current user is trying to delete themself
        DeleteUserUseCase useCase = new DeleteUserUseCase(repository);

        useCase.delete(id);
    }

    @Secured(value="ROLE_"+Profile.Constant.ADMIN)
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        security = {@SecurityRequirement(name = "OIDC")},
        summary = "Update a user",
        description = "**Requires `"+Profile.Constant.ADMIN+"` profile**.\n\n" +
            "Update the specified user by their id. Informs only the fields that is required to be update",
        responses = {
            @ApiResponse(responseCode = "200", description = "User successfully updated"),
            @ApiResponse(responseCode = "404", description = ArchitectureMessage.ENTITY_NOT_FOUND, content={@Content(schema=@Schema(implementation=ApiErrorDTO.class))})
        }
    )
    @PatchMapping(value="/{id}", produces={MediaType.APPLICATION_JSON_VALUE}, consumes={MediaType.APPLICATION_JSON_VALUE})
    public UserResponseDTO patch(
        @PathVariable String id,
        @RequestBody UserUpdateRequestDTO body
    ) {
        UpdateUserUseCase useCase = new UpdateUserUseCase(repository, hash);

        return useCase.update(id, body);
    }
}
