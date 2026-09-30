package it.gruppoinit.pal.gp.areariservata.web.validator;

import it.init.sigepro.rte.types.AltriSoggettiType;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AltriSoggettiPFValidator implements Validator {

    private String azione;

    public AltriSoggettiPFValidator(String azione) {

	this.azione = azione;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return AltriSoggettiType.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	AltriSoggettiType altroSoggettoPF = (AltriSoggettiType) obj;
	if (StringUtils.isBlank(altroSoggettoPF.getSoggetto().getPersonaFisica().getCodiceFiscale())) {
	    e.rejectValue("altroSoggettoPF.soggetto.personaFisica.codiceFiscale", "error.inserire-codice-fiscale");
	} else if (altroSoggettoPF.getSoggetto().getPersonaFisica().getCodiceFiscale().length() != 16) {
	    e.rejectValue("altroSoggettoPF.soggetto.personaFisica.codiceFiscale", "error.codice-fiscale-sedici-caratteri");
	}
	if (StringUtils.isNotBlank(azione) && !azione.equals("FIND_AS_PF")) {
	    if (StringUtils.isBlank(altroSoggettoPF.getSoggetto().getPersonaFisica().getNome())) {
		e.rejectValue("altroSoggettoPF.soggetto.personaFisica.nome", "error.nome-obbligatorio");
	    }
	    if (StringUtils.isBlank(altroSoggettoPF.getSoggetto().getPersonaFisica().getCognome())) {
		e.rejectValue("altroSoggettoPF.soggetto.personaFisica.cognome", "error.cognome-obbligatorio");
	    }
	    if (StringUtils.isBlank(altroSoggettoPF.getTipoRapporto().getRuolo())) {
		e.rejectValue("altroSoggettoPF.tipoRapporto.ruolo", "error.specificare-ruolo");
	    }
	}
    }
}
