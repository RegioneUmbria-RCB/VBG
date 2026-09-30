package it.gruppoinit.pal.gp.gestionecalendari.web.validator;

import it.gruppoinit.pal.gp.core.domain.AnagrafeImpresa;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AnagrafeImpresaValidator implements Validator {

    public boolean supports(Class clazz) {

	return AnagrafeImpresa.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	AnagrafeImpresa fs = (AnagrafeImpresa) obj;
	if (StringUtils.isBlank(fs.getNome())) {
	    e.rejectValue("entity.nome", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(fs.getCognome())) {
	    e.rejectValue("entity.cognome", "validation.campo-obbligatorio");
	}
	if (StringUtils.isBlank(fs.getCodicefiscale())) {
	    e.rejectValue("entity.codicefiscale", "validation.campo-obbligatorio");
	}
    }
}
