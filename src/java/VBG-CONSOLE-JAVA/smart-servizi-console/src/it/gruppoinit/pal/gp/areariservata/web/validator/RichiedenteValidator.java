package it.gruppoinit.pal.gp.areariservata.web.validator;

import it.init.sigepro.rte.types.RichiedenteType;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RichiedenteValidator implements Validator {

    private String azione;

    public RichiedenteValidator(String azione) {

	this.azione = azione;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return RichiedenteType.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	RichiedenteType rich = (RichiedenteType) obj;
	if (StringUtils.isBlank(rich.getAnagrafica().getCodiceFiscale())) {
	    e.rejectValue("richiedente.anagrafica.codiceFiscale", "error.inserire-codice-fiscale");
	} else if (rich.getAnagrafica().getCodiceFiscale().length() != 16) {
	    e.rejectValue("richiedente.anagrafica.codiceFiscale", "error.codice-fiscale-sedici-caratteri");
	}
	if (StringUtils.isNotBlank(azione) && !azione.equals("FIND")) {
	    if (StringUtils.isBlank(rich.getAnagrafica().getNome())) {
		e.rejectValue("richiedente.anagrafica.nome", "error.nome-obbligatorio");
	    }
	    if (StringUtils.isBlank(rich.getAnagrafica().getCognome())) {
		e.rejectValue("richiedente.anagrafica.cognome", "error.cognome-obbligatorio");
	    }
	    if (StringUtils.isBlank(rich.getAnagrafica().getResidenza().getIndirizzo())) {
		e.rejectValue("richiedente.anagrafica.residenza.indirizzo", "error.indirizzo-residenza-obbligatorio");
	    }
	    if (StringUtils.isBlank(rich.getAnagrafica().getResidenza().getComune().getComune())) {
		e.rejectValue("richiedente.anagrafica.residenza.comune.comune", "error.comune-residenza-obbligatorio");
	    }
	}
    }
}
