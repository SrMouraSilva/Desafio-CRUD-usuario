package br.com.srmourasilva.desafio.validation.annotation;

import br.com.srmourasilva.desafio.validation.ErrorMessage;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hibernate.validator.constraints.CompositionType.OR;

/**
 * The annotated element can be a non-empty striny or a {@code null} value.
 *
 * <p>
 * Created because {@code @NotBlank} requires that the element
 * mustn't be {@code null}
 *
 * <p>
 * Based on: <a href="https://stackoverflow.com/a/43716689/1524997">Jules' stackoverflow answer</a>
 */
@ConstraintComposition(OR)
@Null
@NotBlank
@ReportAsSingleViolation
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = { })
public @interface NotBlankNullable {
    String message() default ErrorMessage.NOT_BLANK;
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
