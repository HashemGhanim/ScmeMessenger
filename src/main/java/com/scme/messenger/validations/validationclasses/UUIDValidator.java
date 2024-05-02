package com.scme.messenger.validations.validationclasses;

import com.scme.messenger.validations.UUID;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class UUIDValidator implements ConstraintValidator<UUID , java.util.UUID> {

    private final String regexPattern = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";

    @Override
    public boolean isValid(java.util.UUID value, ConstraintValidatorContext context) {

        if(value.toString().length() == 0)
            return false;

        if(Pattern.matches(regexPattern , value.toString()))
            return true;

        return false;
    }
}
