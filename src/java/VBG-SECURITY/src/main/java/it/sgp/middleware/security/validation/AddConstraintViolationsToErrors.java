package it.sgp.middleware.security.validation;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

public class AddConstraintViolationsToErrors extends SpringValidatorAdapter {

    public AddConstraintViolationsToErrors() {

	super(Validation.buildDefaultValidatorFactory().getValidator()); // Validator is not actually used
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addConstraintViolations(Set<? super ConstraintViolation<?>> violations, Errors errors, String innerEntityName) {

	// Using raw type since processConstraintViolations specifically expects ConstraintViolation<Object>
	String nestedPath = errors.getNestedPath();
	if (StringUtils.isNotBlank(innerEntityName)) {
	    errors.setNestedPath(innerEntityName);
	}
	super.processConstraintViolations((Set) violations, errors);
	if (StringUtils.isNotBlank(innerEntityName)) {
	    errors.setNestedPath(nestedPath);
	}
    }
}
