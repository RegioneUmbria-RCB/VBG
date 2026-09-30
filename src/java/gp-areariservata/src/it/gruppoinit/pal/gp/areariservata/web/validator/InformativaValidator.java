package it.gruppoinit.pal.gp.areariservata.web.validator;

import it.gruppoinit.pal.gp.areariservata.domain.Informativa;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class InformativaValidator implements Validator {

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return Informativa.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	Informativa info = (Informativa) obj;
	if (!info.isAccettata()) {
	    e.rejectValue("informativa.accettata", "error.informativa-non-accettata");
	}
    }
}
