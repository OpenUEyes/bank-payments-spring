package com.company.bps.validators;

import com.company.bps.validators.constraints.PhoneNumberConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {

    @Override
    public void initialize(PhoneNumberConstraint password) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext cxt) {
        return password != null && password.matches("0(39|67|68|96|97|98|50|66|95|99|63|93|91|92|94)[0-9]{7}");
    }
}
