package com.paevolution.appiogateway.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UTCISODateFromStringValidator.class)
@Target(value = { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UTCISODateFromStringConstraint {

    String message() default "Il Formato del campo non è valido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
