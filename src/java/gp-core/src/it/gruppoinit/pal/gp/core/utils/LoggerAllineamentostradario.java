package it.gruppoinit.pal.gp.core.utils;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.helper.AllineamentoStradarioHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerAllineamentostradario {

    private static final Logger log = LoggerFactory.getLogger(LoggerAllineamentostradario.class);

    public static void log(String message) {

	log.debug(message);
    }

    public static void logAllineamentoStradario(String responsabile, List<Comuni> listComuni,
	    Map<String, Map<String, AllineamentoStradarioHelper>> risultato) {

	StringBuffer buffer = new StringBuffer();
	for (Comuni comune : listComuni) {
	    buffer = buffer.append(comune.getComune()).append(", ");
	}
	String comuni = buffer.substring(0, (buffer.length() - 2));
	String message = Utilities.formatMessage(ALLINEAMENTO_STRADARIO_MANUALE, Utilities.getToday(true), responsabile, comuni);
	log(message);
    }

    public static void logAllineamentoStradario(List<Comuni> listComuni, Map<String, Map<String, AllineamentoStradarioHelper>> risultato) {

	// recupero il dettaglio del risultato dell'aggiornamento
	Map<String, AllineamentoStradarioHelper> map = risultato.get("RISULTATO");
	StringBuffer buffer = new StringBuffer();
	List<String> messaggiLogAggiormento = new ArrayList<String>();
	AllineamentoStradarioHelper allineamentoStradarioHelper = null;
	// creo la lista di comuni aggiornati e la stringa di dettaglio di aggiornameno per singolo comune
	for (Comuni comune : listComuni) {
	    // Stringa nome comuni aggiornati
	    buffer = buffer.append(comune.getComune()).append(", ");
	    allineamentoStradarioHelper = new AllineamentoStradarioHelper();
	    allineamentoStradarioHelper = map.get(comune.getCodicecomune());
	    // Dettaglio aggiornamento con errore
	    if (BooleanUtils.toBoolean(allineamentoStradarioHelper.getIsErrore())) {
		List<String> errori = allineamentoStradarioHelper.getErrori();
		StringBuffer stringBuffer = new StringBuffer();
		for (String string : errori) {
		    stringBuffer.append(string).append(",");
		}
		String messLogAgg = Utilities.formatMessage(ALLINEAMENTO_STRADARIO_MESSAGGIO_AGGIORNAMENTO_KO,
			allineamentoStradarioHelper.getComune(), stringBuffer.toString());
		messaggiLogAggiormento.add(messLogAgg);
	    } else {
		// Dettaglio aggiornamento ok
		String messLogAgg = Utilities.formatMessage(ALLINEAMENTO_STRADARIO_MESSAGGIO_AGGIORNAMENTO_OK,
			allineamentoStradarioHelper.getComune(), allineamentoStradarioHelper.getNumAggiornati(),
			allineamentoStradarioHelper.getNumAggiunti());
		messaggiLogAggiormento.add(messLogAgg);
	    }
	}
	String comuni = buffer.substring(0, (buffer.length() - 1));
	String message = Utilities.formatMessage(ALLINEAMENTO_STRADARIO_AUTOMATICO, Utilities.getToday(true), comuni);
	log(message);
	// Ciclo tutti i messaggi di dettaglio aggiornamento e li scrivo su file
	for (String string : messaggiLogAggiormento) {
	    log(string);
	}
    }

    private static final String ALLINEAMENTO_STRADARIO_MANUALE = "In data ''{0}'' l'' operatore ''{1}'' ha effettualo l''allineamento dello stradario per i comuni  ''{2}''";
    private static final String ALLINEAMENTO_STRADARIO_AUTOMATICO = "In data ''{0}'' il processo automatico ha effettualo l''allineamento dello stradario per i comuni  ''{1}''";
    private static final String ALLINEAMENTO_STRADARIO_MESSAGGIO_AGGIORNAMENTO_OK = "''{0}'' : Aggiornati  ''{1}''. Inseriti ''{2}'' ";
    private static final String ALLINEAMENTO_STRADARIO_MESSAGGIO_AGGIORNAMENTO_KO = "''{0}'' : Errori :  ''{1}''.";
}
