package com.company.bps.validators.constraints;

import com.company.bps.validators.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {

    String message() default "Invalid password. Use Latin alphabet. Min 5 symbols, max 20. Allowed using numbers.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}