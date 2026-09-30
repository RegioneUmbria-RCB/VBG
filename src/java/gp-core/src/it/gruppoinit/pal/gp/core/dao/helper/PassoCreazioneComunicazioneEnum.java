package it.gruppoinit.pal.gp.core.dao.helper;

public enum PassoCreazioneComunicazioneEnum {
    VERIFICA_PRESENZA_ISTANZA("Verifica presenza istanza"), INSERT_MOVIMENTO("Inserimento movimento"), PROTOCOLLAZIONE_MOVIMENTO(
	    "Protocollazione movimento"), CREAZIONE_ALLEGATO("Creazione allegato"), CONVERSIONE_PDF_ALLEGATO("Conversione in PDF dell'allegato"), FIRMA_DOCUMENTI(
	    "Firma degli allegati generati"), INVIO_MAIL("Invio mail"), PROTOCOLLAZIONE_MOVIMENTO_PRIMA_CREAZIONE_ALLEGATO(
	    "Protocollazione movimento prima creazione allegato"), ELABORAZIONE_COMPLETA("Elaborazione completa");

    private String message;

    private PassoCreazioneComunicazioneEnum(String message) {

	this.message = message;
    }
}
