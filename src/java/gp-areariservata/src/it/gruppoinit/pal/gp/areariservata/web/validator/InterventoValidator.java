package it.gruppoinit.pal.gp.areariservata.web.validator;

import it.init.sigepro.rte.types.InterventoType;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class InterventoValidator implements Validator {

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return InterventoType.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	InterventoType intervento = (InterventoType) obj;
	if (StringUtils.isBlank(intervento.getCodice())) {
	    e.rejectValue("intervento.descrizione", "error.intervento-non-selezionato");
	}
    }
}
