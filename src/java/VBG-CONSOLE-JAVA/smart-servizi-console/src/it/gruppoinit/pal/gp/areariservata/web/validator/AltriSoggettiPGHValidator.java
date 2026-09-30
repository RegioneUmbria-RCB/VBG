package it.gruppoinit.pal.gp.areariservata.web.validator;

import it.gruppoinit.pal.gp.areariservata.domain.AltriSoggettiTypeHelper;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AltriSoggettiPGHValidator implements Validator {

    private String azione;
    private Map<String, String> parametri;

    private enum CampiDaValidarePG {
	NATURA_GIURIDICA, SEDE_LEGALE, IND_CORRISPONDENZA, TEL, FAX, CCIAA, REA, EMAIL, PEC
    };

    public AltriSoggettiPGHValidator(String azione) {

	this.azione = azione;
	this.parametri = new HashMap<String, String>();
    }

    public AltriSoggettiPGHValidator(String azione, Map<String, String> parametri) {

	this.azione = azione;
	this.parametri = parametri;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return AltriSoggettiTypeHelper.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	AltriSoggettiTypeHelper altroSoggettoPGH = (AltriSoggettiTypeHelper) obj;
	if ("VALIDA_RICERCA".equals(azione) || "VALIDA_SOGGETTO".equals(azione)) {
	    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getCodiceFiscale())) {
		if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getPartitaIva())) {
		    e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.partitaIva", "error.inserire-codice-fiscale-o-piva");
		} else if (altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getPartitaIva().length() != 11) {
		    e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.partitaIva", "error.piva-da-undici-caratteri");
		}
	    } else if (altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getCodiceFiscale().length() < 11) {
		e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.codiceFiscale", "error.cf-da-undici-caratteri");
	    }
	}
	if ("VALIDA_SOGGETTO".equals(azione)) {
	    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getRagioneSociale())) {
		e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.ragioneSociale", "error.specificare-ragione-sociale");
	    }
	    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getTipoRapporto().getIdRuolo())) {
		e.rejectValue("altroSoggettoPGH.soggetto.tipoRapporto.ruolo", "error.specificare-ruolo");
	    }
	}
	for (Map.Entry<String, String> param : parametri.entrySet()) {
	    if (param.getKey().startsWith("OBBLIGATORIO_PG_")) {
		//
		String key = param.getKey().substring(16);
		switch (CampiDaValidarePG.valueOf(key)) {
		case CCIAA:
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getIscrizioneCCIAA().getNumero()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.iscrizioneCCIAA.numero", "error.cciaa-numero-obbligatorio");
		    if (altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getIscrizioneCCIAA().getData() == null)
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.iscrizioneCCIAA.data", "error.cciaa-data-obbligatorio");
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getIscrizioneCCIAA().getComune()
			    .getCodiceCatastale()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.iscrizioneCCIAA.comune.comune",
				"error.cciaa-comune-obbligatorio");
		    break;
		case IND_CORRISPONDENZA:
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getIndirizzoCorrispondenza().getCap()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.indirizzoCorrispondenza.cap",
				"error.corrispondenza-cap-obbligatorio");
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getIndirizzoCorrispondenza()
			    .getIndirizzo()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.indirizzoCorrispondenza.indirizzo",
				"error.corrispondenza-indirizzo-obbligatorio");
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getIndirizzoCorrispondenza()
			    .getProvincia()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.indirizzoCorrispondenza.provincia",
				"error.corrispondenza-provincia-obbligatorio");
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getIndirizzoCorrispondenza()
			    .getComune().getCodiceCatastale()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.indirizzoCorrispondenza.comune.comune",
				"error.corrispondenza-comune-obbligatorio");
		    break;
		case NATURA_GIURIDICA:
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getNaturaGiuridica()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.naturaGiuridica", "error.natura-giuridica-obbligatorio");
		    break;
		case REA:
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getIscrizioneREA().getNumero()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.iscrizioneREA.numero", "error.rea-numero-obbligatorio");
		    if (altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getIscrizioneREA().getData() == null)
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.iscrizioneREA.data", "error.rea-data-obbligatorio");
		    if (StringUtils
			    .isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getIscrizioneREA().getSiglaProvincia()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.iscrizioneREA.siglaProvincia",
				"error.rea-provincia-obbligatorio");
		    break;
		case SEDE_LEGALE:
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getSedeLegale().getCap()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.sedeLegale.cap", "error.sede-legale-cap-obbligatorio");
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getSedeLegale().getIndirizzo()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.sedeLegale.indirizzo",
				"error.sede-legale-indirizzo-obbligatorio");
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getSedeLegale().getProvincia()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.sedeLegale.provincia",
				"error.sede-legale-provincia-obbligatorio");
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getSedeLegale().getComune()
			    .getCodiceCatastale()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.sedeLegale.comune.comune",
				"error.sede-legale-comune-obbligatorio");
		    break;
		case TEL:
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getTelefono()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.telefono", "error.tel-obbligatorio");
		    break;
		case EMAIL:
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getEmail()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.email", "error.email-obbligatorio");
		    break;
		case FAX:
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getFax()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.fax", "error.fax-obbligatorio");
		    break;
		case PEC:
		    if (StringUtils.isBlank(altroSoggettoPGH.getSoggetto().getSoggetto().getPersonaGiuridica().getPec()))
			e.rejectValue("altroSoggettoPGH.soggetto.soggetto.personaGiuridica.pec", "error.pec-obbligatorio");
		    break;
		default:
		    break;
		}
	    }
	}
    }
}
