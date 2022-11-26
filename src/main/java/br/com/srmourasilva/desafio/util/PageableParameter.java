package br.com.srmourasilva.desafio.util;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Parameters({
    @Parameter(
        name = "page", in = ParameterIn.QUERY, description = "Number of page of results you want to retrieve (0..N)",
        schema = @Schema(implementation = Integer.class, type = "int", defaultValue = "0", minimum = "0")
    ),
    @Parameter(
        name = "size", in = ParameterIn.QUERY, description = "Maximum number of records per page",
        schema = @Schema(implementation = Integer.class, type = "int", defaultValue = "10", minimum = "1")
    ),
    @Parameter(
        name = "sort", in = ParameterIn.QUERY,
        description = "" +
            "Ordering criteria in the format: `{property},[asc | desc]`.\n\n" +
            "" +
            "Default sort order is ascending (`asc`). " +
            "Multiple sorting criteria are supported."
        ,
        schema = @Schema(implementation = String.class, type = "string")
    )
})
public @interface PageableParameter {
}