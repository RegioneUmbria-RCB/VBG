package it.gruppoinit.pal.gp.gestionecalendari.web.validator;

import it.gruppoinit.pal.gp.core.domain.Responsabili;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ResponsabiliValidator implements Validator {

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return Responsabili.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	Responsabili resp = (Responsabili) obj;
	if (StringUtils.isBlank(resp.getResponsabile())) {
	    e.rejectValue("responsabili.responsabile", "validation.responsabile-obbligatorio");
	}
	if (StringUtils.isBlank(resp.getUserid())) {
	    e.rejectValue("responsabili.userid", "validation.userid-obbligatorio");
	}
    }
}
