package com.company.bps.validators;

import com.company.bps.validators.constraints.LoginConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginValidator implements ConstraintValidator<LoginConstraint, String> {

    @Override
    public void initialize(LoginConstraint login) {
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext cxt) {
        return login != null && login.matches("[A-Za-z0-9][A-Za-z0-9_]{2,13}[A-Za-z0-9]");
    }
}
