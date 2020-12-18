package pl.zespolowe.splix.config.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ColorsFormatValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Colors {
    String message() default "Invalid colors format, should be 6x6 in csv";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
