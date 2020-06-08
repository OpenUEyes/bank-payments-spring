package com.company.bps.validators.constraints;

import com.company.bps.validators.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberConstraint {

    String message() default "Invalid phone number. Use format 0xx1234567 where xx - operator code. Example: 0981773984";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}