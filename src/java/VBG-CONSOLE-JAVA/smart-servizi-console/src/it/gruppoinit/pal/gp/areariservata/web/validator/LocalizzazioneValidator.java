package it.gruppoinit.pal.gp.areariservata.web.validator;

import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class LocalizzazioneValidator implements Validator {

    private Map<String, String> parametri;

    private enum CampiDaValidare {
	VIA, CIVICO, ESPONENTE, SCALA, INTERNO, ESPONENTE_INTERNO, PIANO, COLORE, FRAZIONE, QUARTIERE, CIRCOSCRIZIONE, DATI_CATASTALI
    };

    public LocalizzazioneValidator(Map<String, String> parametri) {

	this.parametri = parametri;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return LocalizzazioneNelComuneType.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	LocalizzazioneNelComuneType loc = (LocalizzazioneNelComuneType) obj;
	for (Map.Entry<String, String> param : parametri.entrySet()) {
	    if (param.getKey().startsWith("OBBLIGATORIO_")) {
		//
		String key = param.getKey().substring(13);
		switch (CampiDaValidare.valueOf(key)) {
		case VIA:
		    if (StringUtils.isBlank(loc.getDenominazione()))
			e.rejectValue("localizzazione.denominazione", "error.denominazione-obbligatorio");
		    break;
		case CIVICO:
		    if (StringUtils.isBlank(loc.getCivico()))
			e.rejectValue("localizzazione.civico", "error.civico-obbligatorio");
		    break;
		case ESPONENTE:
		    if (StringUtils.isBlank(loc.getEsponente()))
			e.rejectValue("localizzazione.esponente", "error.esponente-obbligatorio");
		    break;
		case SCALA:
		    if (StringUtils.isBlank(loc.getScala()))
			e.rejectValue("localizzazione.scala", "error.scala-obbligatorio");
		    break;
		case INTERNO:
		    if (StringUtils.isBlank(loc.getInterno()))
			e.rejectValue("localizzazione.interno", "error.interno-obbligatorio");
		    break;
		case ESPONENTE_INTERNO:
		    if (StringUtils.isBlank(loc.getEsponenteInterno()))
			e.rejectValue("localizzazione.esponenteInterno", "error.esponente-interno-obbligatorio");
		    break;
		case PIANO:
		    if (StringUtils.isBlank(loc.getPiano()))
			e.rejectValue("localizzazione.piano", "error.piano-obbligatorio");
		    break;
		case COLORE:
		    if (StringUtils.isBlank(loc.getColore()))
			e.rejectValue("localizzazione.colore", "error.colore-obbligatorio");
		    break;
		case FRAZIONE:
		    if (StringUtils.isBlank(loc.getFrazione().getDescrizione()))
			e.rejectValue("localizzazione.frazione.descrizione", "error.frazione-obbligatorio");
		    break;
		case QUARTIERE:
		    if (StringUtils.isBlank(loc.getQuartiere().getDescrizione()))
			e.rejectValue("localizzazione.quartiere.descrizione", "error.quartiere-obbligatorio");
		    break;
		case CIRCOSCRIZIONE:
		    if (StringUtils.isBlank(loc.getCircoscrizione().getDescrizione()))
			e.rejectValue("localizzazione.circoscrizione.descrizione", "error.circoscrizione-obbligatorio");
		    break;
		case DATI_CATASTALI:
		    //non posso validarlo qui, lo valido nel controller
		    break;
		default:
		    break;
		}
	    }
	}
    }
}