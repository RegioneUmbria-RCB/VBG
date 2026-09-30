package it.gruppoinit.pal.gp.gestionecalendari.web.validator;

import it.gruppoinit.pal.gp.core.domain.ManifAreePubbliche;
import it.gruppoinit.pal.gp.core.helper.EntityUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ManifAreePubblicheValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {

	return ManifAreePubbliche.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	ManifAreePubbliche fs = (ManifAreePubbliche) obj;
	if (fs.getDataInserimento() == null) {
	    e.rejectValue("entity.dataInserimento", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(fs.getDenominazione())) {
	    e.rejectValue("entity.denominazione", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(fs.getTipologia())) {
	    e.rejectValue("entity.tipologia", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(fs.getCadenza())) {
	    e.rejectValue("entity.cadenza", "validation.campo-obbligatorio");
	}
	if (EntityUtils.isNestedPropertyBlank(fs.getComuni(), "codicecomune")) {
	    e.rejectValue("entity.comuni.codicecomune", "validation.campo-obbligatorio");
	}
    }
}
