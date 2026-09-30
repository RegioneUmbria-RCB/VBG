package it.gruppoinit.pal.gp.areariservata.web.validator;

import it.init.sigepro.rte.types.AltriSoggettiType;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AltriSoggettiPGValidator implements Validator {

    private String azione;

    public AltriSoggettiPGValidator(String azione) {

	this.azione = azione;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return AltriSoggettiType.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	AltriSoggettiType altroSoggettoPG = (AltriSoggettiType) obj;
	if (StringUtils.isBlank(altroSoggettoPG.getSoggetto().getPersonaGiuridica().getCodiceFiscale())) {
	    if (StringUtils.isBlank(altroSoggettoPG.getSoggetto().getPersonaGiuridica().getPartitaIva())) {
		e.rejectValue("altroSoggettoPG.soggetto.personaGiuridica.partitaIva", "error.inserire-codice-fiscale-o-piva");
	    } else if (altroSoggettoPG.getSoggetto().getPersonaGiuridica().getPartitaIva().length() != 11) {
		e.rejectValue("altroSoggettoPG.soggetto.personaGiuridica.partitaIva", "error.piva-da-undici-caratteri");
	    }
	} else if (altroSoggettoPG.getSoggetto().getPersonaGiuridica().getCodiceFiscale().length() < 11) {
	    e.rejectValue("altroSoggettoPG.soggetto.personaGiuridica.codiceFiscale", "error.cf-da-undici-caratteri");
	}
	if (StringUtils.isNotBlank(azione) && !azione.equals("FIND_AS_PG")) {
	    if (StringUtils.isBlank(altroSoggettoPG.getSoggetto().getPersonaGiuridica().getRagioneSociale())) {
		e.rejectValue("altroSoggettoPG.soggetto.personaGiuridica.ragioneSociale", "error.specificare-ragione-sociale");
	    }
	    if (StringUtils.isBlank(altroSoggettoPG.getTipoRapporto().getRuolo())) {
		e.rejectValue("altroSoggettoPG.tipoRapporto.ruolo", "error.specificare-ruolo");
	    }
	}
    }
}
