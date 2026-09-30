package it.gruppoinit.pal.gp.areariservata.web.validator;

import it.gruppoinit.pal.gp.areariservata.domain.AltriSoggettiTypeHelper;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AltriSoggettiPFHValidator implements Validator {

    private String azione;
    private Map<String, String> parametri;

    private enum CampiDaValidarePF {
	TITOLO, RESIDENZA, CORRISPONDENZA, CITTADINANZA, TEL, EMAIL, PEC, DATI_ALBO
    };

    public AltriSoggettiPFHValidator(String azione) {

	this.azione = azione;
	this.parametri = new HashMap<String, String>();
    }

    public AltriSoggettiPFHValidator(String azione, Map<String, String> parametri) {

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

	AltriSoggettiTypeHelper altroSoggettoPFH = (AltriSoggettiTypeHelper) obj;
	if ("VALIDA_RICERCA".equals(azione) || "VALIDA_SOGGETTO".equals(azione)) {
	    if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getCodiceFiscale())) {
		e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.codiceFiscale", "error.inserire-codice-fiscale");
	    } else if (altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getCodiceFiscale().length() != 16) {
		e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.codiceFiscale", "error.codice-fiscale-sedici-caratteri");
	    }
	}
	if ("VALIDA_SOGGETTO".equals(azione)) {
	    if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getNome())) {
		e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.nome", "error.nome-obbligatorio");
	    }
	    if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getCognome())) {
		e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.cognome", "error.cognome-obbligatorio");
	    }
	    if (altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getDataNascita() == null) {
		e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.dataNascita", "error.data-nascita-obbligatorio");
	    }
	    if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getComuneNascita().getCodiceCatastale())) {
		e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.comuneNascita.comune", "error.comune-nascita-obbligatorio");
	    }
	    if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getTipoRapporto().getIdRuolo())) {
		e.rejectValue("altroSoggettoPFH.soggetto.tipoRapporto.ruolo", "error.specificare-ruolo");
	    }
	    //ciclare i parametri per trovare quelli che iniziano con OBBLIGATORIO_PF
	    //estrarre l'ultima parte es OBBLIGATORIO_PF_RESIDENZA -> RESIDENZA
	    //validare il relativo campo se il valore del parametro è = 1
	    for (Map.Entry<String, String> param : parametri.entrySet()) {
		if (param.getKey().startsWith("OBBLIGATORIO_PF")) {
		    //
		    String key = param.getKey().substring(16);
		    switch (CampiDaValidarePF.valueOf(key)) {
		    case TITOLO:
			if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getTitolo()))
			    e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.titolo", "error.titolo-obbligatorio");
			break;
		    case CITTADINANZA:
			if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getCittadinanza().getId()))
			    e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.cittadinanza.descrizione",
				    "error.cittadinanza-obbligatorio");
			break;
		    case RESIDENZA:
			if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getResidenza().getCap()))
			    e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.residenza.cap", "error.residenza-cap-obbligatorio");
			if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getResidenza().getIndirizzo()))
			    e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.residenza.indirizzo",
				    "error.residenza-indirizzo-obbligatorio");
			if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getResidenza().getProvincia()))
			    e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.residenza.provincia",
				    "error.residenza-provincia-obbligatorio");
			if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getResidenza().getComune()
				.getCodiceCatastale()))
			    e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.residenza.comune.comune",
				    "error.residenza-comune-obbligatorio");
			break;
		    case EMAIL:
			if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getEmail()))
			    e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.email", "error.email-obbligatorio");
			break;
		    case PEC:
			if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getPec()))
			    e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.pec", "error.pec-obbligatorio");
			break;
		    case TEL:
			if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getTelefono()))
			    e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.telefono", "error.tel-obbligatorio");
			break;
		    case DATI_ALBO:
			if (BooleanUtils.isTrue(altroSoggettoPFH.getTipoSoggetto().getFlgDatialbo())) {
			    if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getDatiIscrizioneAlbo()
				    .getNumeroIscrizione())) {
				e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.datiIscrizioneAlbo.numeroIscrizione",
					"error.num-albo-obbligatorio");
			    }
			    if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getDatiIscrizioneAlbo()
				    .getSiglaProvincia())) {
				e.rejectValue("altroSoggettoPFH.soggetto.soggetto.personaFisica.datiIscrizioneAlbo.siglaProvincia",
					"error.provincia-albo-obbligatorio");
			    }
			    if (StringUtils.isBlank(altroSoggettoPFH.getSoggetto().getSoggetto().getPersonaFisica().getDatiIscrizioneAlbo()
				    .getTipoOrdineProfessionisti().getDescrizione())) {
				e.rejectValue(
					"altroSoggettoPFH.soggetto.soggetto.personaFisica.datiIscrizioneAlbo.tipoOrdineProfessionisti.descrizione",
					"error.ordine-albo-obbligatorio");
			    }
			}
			break;
		    default:
			break;
		    }
		}
	    }
	}
    }
}
