package it.gruppoinit.pal.gp.areariservata.web.validator;

import it.init.sigepro.rte.types.PersonaFisicaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class IntermediarioValidator implements Validator {

    private String azione;

    public IntermediarioValidator(String azione) {

	this.azione = azione;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return PersonaFisicaType.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	PersonaFisicaType intermediario = (PersonaFisicaType) obj;
	if (StringUtils.isBlank(intermediario.getCodiceFiscale())) {
	    e.rejectValue("intermediario.codiceFiscale", "error.inserire-codice-fiscale");
	} else if (intermediario.getCodiceFiscale().length() != 16) {
	    e.rejectValue("intermediario.codiceFiscale", "error.codice-fiscale-sedici-caratteri");
	}
	if (StringUtils.isNotBlank(azione) && !azione.equals("FIND_INT")) {
	    if (StringUtils.isBlank(intermediario.getNome())) {
		e.rejectValue("intermediario.nome", "error.nome-obbligatorio");
	    }
	    if (StringUtils.isBlank(intermediario.getCognome())) {
		e.rejectValue("intermediario.cognome", "error.cognome-obbligatorio");
	    }
	}
    }
}
