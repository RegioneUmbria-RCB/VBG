package it.gruppoinit.pal.gp.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerCancellazioni {

    public static enum TIPO_DOCUMENTO {
	Documento,
	Allegato_Endo,
	Allegato_Movimento,
	Registrazioni_adeguamento_Iva
    }

    protected static final Logger log = LoggerFactory.getLogger("it.gruppoinit.auditing.dml_activity");

    public static void log(String message) {

	log.error(message);
    }

    public static void logCancellazioneDocumentoistanza(String responsabile, String descrizioneDocumento, String descrizioneIstanza,
	    TIPO_DOCUMENTO tipoDocumento) {

	String message = Utilities.formatMessage(CANCELLAZIONE_DOCUMENTOISTANZA, Utilities.getToday(true), responsabile, tipoDocumento,
		descrizioneDocumento, descrizioneIstanza);
	log(message);
    }

    public static void logCancellazioneDocumentiistanza(String responsabile, String descrizioneDocumento, String descrizioneIstanza,
	    TIPO_DOCUMENTO tipoDocumento) {

	String message = Utilities.formatMessage(CANCELLAZIONE_DOCUMENTIISTANZA, Utilities.getToday(true), responsabile, tipoDocumento,
		descrizioneDocumento, descrizioneIstanza);
	log(message);
    }

    public static void logCancellazioneIstanza(String responsabile, String descrizioneIstanza) {

	String message = Utilities.formatMessage(CANCELLAZIONE_ISTANZA, Utilities.getToday(true), responsabile, descrizioneIstanza);
	log(message);
    }

    public static void logModificaSoftwareIstanza(String responsabile, String descrizioneIstanza, String nuovoSoftware) {

	String message = Utilities.formatMessage(MODIFICA_SOFTWARE_ISTANZA, Utilities.getToday(true), responsabile, descrizioneIstanza,
		nuovoSoftware);
	log(message);
    }

    public static void logModificaComuneIstanza(String responsabile, String descrizioneIstanza, String nuovoComune) {

	String message = Utilities.formatMessage(MODIFICA_COMUNE_ISTANZA, Utilities.getToday(true), responsabile, descrizioneIstanza, nuovoComune);
	log(message);
    }

    public static void logCancellazioneIstanzeprocedimenti(String responsabile, String descrizioneIstanzeprocedimenti, String descrizioneIstanza) {

	String message = Utilities.formatMessage(CANCELLAZIONE_ISTANZEPROCEDIMENTI, Utilities.getToday(true), responsabile,
		descrizioneIstanzeprocedimenti, descrizioneIstanza);
	log(message);
    }

    public static void logCancellazioneMail(String responsabile, String descrizioneMail, String descrizioneIstanza) {

	String message = Utilities.formatMessage(CANCELLAZIONE_MAIL, Utilities.getToday(true), responsabile, descrizioneMail, descrizioneIstanza);
	log(message);
    }

    public static void logCancellazioneMovimento(String responsabile, String descrizioneMovimento, String descrizioneIstanza) {

	String message = Utilities.formatMessage(CANCELLAZIONE_MOVIMENTI, Utilities.getToday(true), responsabile, descrizioneMovimento,
		descrizioneIstanza);
	log(message);
    }

    public static void logCancellazioneMovimentiAllegati(String responsabile, String descrizioneAllegato, String descrizioneMovimento,
	    String descrizioneIstanza) {

	String message = Utilities.formatMessage(CANCELLAZIONE_MOVIMENTI_ALLEGATI, Utilities.getToday(true), responsabile, descrizioneAllegato,
		descrizioneMovimento, descrizioneIstanza);
	log(message);
    }

    public static void logCancellazioneProcura(String responsabile, String descrizioneProcura, String descrizioneIstanza) {

	String message = Utilities.formatMessage(CANCELLAZIONE_PROCURA, Utilities.getToday(true), responsabile, descrizioneProcura,
		descrizioneIstanza);
	log(message);
    }

    public static void logRegistrazioniAdeguamentoIva(String responsabile, String valoreIva, String software) {

	String message = Utilities.formatMessage(REGISTRAZIONI_ADEGUAMENTO_IVA, Utilities.getToday(true), responsabile, valoreIva, software);
	log(message);
    }

    public static void logAggiornaOnereistanza(String responsabile, String messaggio) {

	String message = Utilities.formatMessage(ISTANZEONERI_AGGIORNAMENTO, Utilities.getToday(true), responsabile, messaggio);
	log(message);
    }

    public static void logAggiornaNoteMovimentoStc(String responsabile, String messaggio) {

	String message = Utilities.formatMessage(NOTE_MOVIMENTO_STC_AGGIORNAMENTO, Utilities.getToday(true), responsabile, messaggio);
	log(message);
    }

    public static void logCancellazioneGraduatoriaBando(String responsabile, String descGraduatoria, String descBando) {

	String message = Utilities.formatMessage(GRADUATORIE_BANDO, Utilities.getToday(true), responsabile, descGraduatoria, descBando);
	log(message);
    }

    public static void logImpostaPecNonInEvidenza(String responsabile, String responsabileEvidenza, String codicePec) {

	String message = Utilities.formatMessage(IMPOSTA_PEC_NON_IN_EVIDENZA, Utilities.getToday(true), codicePec, responsabileEvidenza,
		responsabile);
	log(message);
    }

    public static void logSpostamentoAlberoProc(String responsabile, String nuovoRamo, String vecchioRamo) {

	String message = Utilities.formatMessage(SPOSTAMENTO_ALBEROPROC, Utilities.getToday(true), responsabile, vecchioRamo, nuovoRamo);
	log(message);
    }

    private static final String CANCELLAZIONE_DOCUMENTOISTANZA = "In data ''{0}'' l'' operatore ''{1}'' ha cancellato il {2} ''{3}'' dell''istanza ''{4}''";
    private static final String CANCELLAZIONE_DOCUMENTIISTANZA = "In data ''{0}'' l'' operatore ''{1}'' ha cancellato i seguenti {2} ''{3}'' dell''istanza ''{4}''";
    private static final String CANCELLAZIONE_ISTANZA = "In data ''{0}'' l'' operatore ''{1}'' ha cancellato l''istanza ''{2}''";
    private static final String MODIFICA_SOFTWARE_ISTANZA = "In data ''{0}'' l'' operatore ''{1}'' ha modificato il software dell''istanza ''{2}'' al nuovo software ''{3}''";
    private static final String CANCELLAZIONE_ISTANZEPROCEDIMENTI = "In data ''{0}'' l'' operatore ''{1}'' ha cancellato l''endo ''{2}'' dell''istanza ''{3}''";
    private static final String CANCELLAZIONE_MAIL = "In data ''{0}'' l'' operatore ''{1}'' ha cancellato la mail ''{2}'' per l''istanza ''{3}''";
    private static final String CANCELLAZIONE_MOVIMENTI = "In data ''{0}'' l'' operatore ''{1}'' ha cancellato il movimento ''{2}'' dell''istanza ''{3}''";
    private static final String CANCELLAZIONE_MOVIMENTI_ALLEGATI = "In data ''{0}'' l'' operatore ''{1}'' ha cancellato l''allegato ''{2}'' del movimento ''{3}'' dell''istanza ''{4}''";
    private static final String CANCELLAZIONE_PROCURA = "In data ''{0}'' l'' operatore ''{1}'' ha cancellato la mail ''{2}'' per l''istanza ''{3}''";
    private static final String REGISTRAZIONI_ADEGUAMENTO_IVA = "In data ''{0}'' l'' operatore ''{1}'' ha effettuato l''adeguamento IVA al ''{2}''% per il software ''{3}''";
    private static final String ISTANZEONERI_AGGIORNAMENTO = "In data ''{0}'' l'' operatore ''{1}'' ha effettuato l''aggiornamento dell''onere {2}";
    private static final String NOTE_MOVIMENTO_STC_AGGIORNAMENTO = "In data ''{0}'' l'' operatore ''{1}'' ha effettuato l''aggiornamento della nota del movimento {2}";
    private static final String GRADUATORIE_BANDO = "In data ''{0}'' l'' operatore ''{1}'' ha eliminato la graduatoria: ''{2}'' del bando ''{3}''";
    private static final String MODIFICA_COMUNE_ISTANZA = "In data ''{0}'' l'' operatore ''{1}'' ha modificato il comune dell''istanza ''{2}'' al nuovo comune ''{3}''";
    private static final String IMPOSTA_PEC_NON_IN_EVIDENZA = "In data ''{0}'' la PEC ''{1}'' che era stata messa in evidenza dall''operatore ''{2}'' é stata impostata come non in evidenza dall''operatore ''{3}''";
    private static final String SPOSTAMENTO_ALBEROPROC = "#SPOSTAMENTO_ALBEROPROC# In data ''{0}'' l''operatore ''{1}'' ha effettuato lo spostamento del ramo ''{2}'' sotto il ramo ''{3}''";
}
