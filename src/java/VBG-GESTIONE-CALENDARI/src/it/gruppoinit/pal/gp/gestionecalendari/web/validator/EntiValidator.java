package it.gruppoinit.pal.gp.gestionecalendari.web.validator;

import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.helper.EntityUtils;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EntiValidator implements Validator {

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return Comuniassociati.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	Comuniassociati com = (Comuniassociati) obj;
	if (EntityUtils.isNestedPropertyBlank(com.getId(), "codicecomune")) {
	    e.rejectValue("comuniassociati.id.codicecomune", "validation.comune-obbligatorio");
	}
    }
}
