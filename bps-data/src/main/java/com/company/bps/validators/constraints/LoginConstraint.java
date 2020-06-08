package com.company.bps.validators.constraints;

import com.company.bps.validators.LoginValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LoginValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginConstraint {

    String message() default "Invalid login. Use Latin alphabet. Min 4 symbols, max 15. Allowed using numbers.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}