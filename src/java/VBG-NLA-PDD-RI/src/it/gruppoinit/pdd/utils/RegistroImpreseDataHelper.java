package it.gruppoinit.pdd.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistroImpreseDataHelper {
    private static Logger log = LoggerFactory.getLogger(RegistroImpreseDataHelper.class);
    private Map<String, String> qualifiche = new HashMap<String, String>();

    public RegistroImpreseDataHelper() {

	popolateQualificheDefault();
    }

    public Map<String, String> getQualifiche() {

	if (qualifiche == null) {
	    popolateQualificheDefault();
	}
	return qualifiche;
    }

    public void setQualifiche(Map<String, String> qualifiche) {

	this.qualifiche = qualifiche;
    }

    public static final String QUALIFICA_DEFAULT_VALUE_ALTRO = "ALTRO PREVISTO DALLA VIGENTE NORMATIVA";

    private void popolateQualificheDefault() {

	qualifiche = new HashMap<String, String>();
	qualifiche.put("ALTRO PREVISTO DALLA VIGENTE NORMATIVA", "ALTRO PREVISTO DALLA VIGENTE NORMATIVA");
	qualifiche.put("AMMINISTRATORE", "AMMINISTRATORE");
	qualifiche.put("ASSOCIAZIONE DI CATEGORIA", "ASSOCIAZIONE DI CATEGORIA");
	qualifiche.put("CENTRO ELABORAZIONE DATI", "CENTRO ELABORAZIONE DATI");
	qualifiche.put("COMMISSARIO GIUDIZIARIO", "COMMISSARIO GIUDIZIARIO");
	qualifiche.put("CONSULENTE", "CONSULENTE");
	qualifiche.put("CURATORE FALLIMENTARE", "CURATORE FALLIMENTARE");
	qualifiche.put("DELEGATO", "DELEGATO");
	qualifiche.put("LEGALE RAPPRESENTANTE", "LEGALE RAPPRESENTANTE");
	qualifiche.put("LIQUIDATORE", "LIQUIDATORE");
	qualifiche.put("NOTAIO", "NOTAIO");
	qualifiche.put("PROFESSIONISTA INCARICATO", "PROFESSIONISTA INCARICATO");
	qualifiche.put("SOCIO", "SOCIO");
	qualifiche.put("STUDIO ASSOCIATO", "STUDIO ASSOCIATO");
	qualifiche.put("TITOLARE", "TITOLARE");
    }

    public static String normalizzaProtocollo(String numeroProtocollo, boolean isEffettuaValidazione) {

	log.debug("normalizzaProtocollo# Validazione attiva {}", isEffettuaValidazione);
	if (StringUtils.isBlank(numeroProtocollo) && isEffettuaValidazione) {
	    Utilities.logAndThrowException(
		    "Non è stato settato il numero protocollo dell'istanza. E' obbligatorio per la comunicazione con il Registro Imprese.",
		    RegistroImpreseDataHelper.class);
	}
	String patternStr = "^([0-9]+)";
	Pattern pattern = Pattern.compile(patternStr);
	Matcher matcher = pattern.matcher(numeroProtocollo);
	if (matcher.find()) {
	    numeroProtocollo = (matcher.group());
	}
	if (numeroProtocollo.length() > 7 && isEffettuaValidazione) {
	    Utilities.logAndThrowException(
		    "Numero protocollo dell'istanza non valido (> 7 caratteri). E' obbligatorio per la comunicazione con il Registro Imprese.",
		    RegistroImpreseDataHelper.class);
	}
	if (numeroProtocollo.length() < 7) {
	    numeroProtocollo = StringUtils.repeat("0", (7 - numeroProtocollo.length())) + numeroProtocollo;
	}
	return numeroProtocollo;
    }
}
