package com.scme.messenger.validations;


import com.scme.messenger.validations.validationclasses.UUIDValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD , ElementType.FIELD , ElementType.PARAMETER , ElementType.ANNOTATION_TYPE})
@Documented
@Constraint(validatedBy = UUIDValidator.class)
public @interface UUID {
    String message() default "UUID has wrong format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
