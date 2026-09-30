package it.gruppoinit.pal.gp.areariservata.web.validator;

import it.init.sigepro.rte.types.PersonaGiuridicaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AziendaRichiedenteValidator implements Validator {

    private String azione;

    public AziendaRichiedenteValidator(String azione) {

	this.azione = azione;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return PersonaGiuridicaType.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	PersonaGiuridicaType azRich = (PersonaGiuridicaType) obj;
	if (StringUtils.isBlank(azRich.getCodiceFiscale())) {
	    if (StringUtils.isBlank(azRich.getPartitaIva())) {
		e.rejectValue("aziendaRichiedente.partitaIva", "error.inserire-codice-fiscale-o-piva");
	    } else if (azRich.getPartitaIva().length() != 11) {
		e.rejectValue("aziendaRichiedente.partitaIva", "error.piva-da-undici-caratteri");
	    }
	} else if (azRich.getCodiceFiscale().length() < 11) {
	    e.rejectValue("aziendaRichiedente.codiceFiscale", "error.cf-da-undici-caratteri");
	}
	if (StringUtils.isNotBlank(azione) && !azione.equals("FIND")) {
	    if (StringUtils.isBlank(azRich.getPartitaIva())) {
		e.rejectValue("aziendaRichiedente.partitaIva", "error.specificare-piva");
	    }
	    if (StringUtils.isBlank(azRich.getRagioneSociale())) {
		e.rejectValue("aziendaRichiedente.ragioneSociale", "error.specificare-ragione-sociale");
	    }
	}
    }
}
