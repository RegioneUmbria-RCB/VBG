package it.gruppoinit.pal.gp.backoffice.web.validator;

import it.gruppoinit.pal.gp.core.domain.Tipibando;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("tipibandoValidator")
public class TipibandoValidator implements Validator {

    @SuppressWarnings("unchecked")
    public boolean supports(Class arg) {

	return Tipibando.class.isAssignableFrom(arg);
    }

    public void validate(Object object, Errors errors) {

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descrizione", "field.required");
    }
}
