package com.company.bps.validators;

import com.company.bps.validators.constraints.PasswordConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint password) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext cxt) {
        return password != null && password.matches("[A-Za-z0-9]{5,20}");
    }
}
