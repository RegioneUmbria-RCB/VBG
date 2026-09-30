package it.gruppoinit.pal.gp.core.dao.helper;

public enum PassoCreazioneComunicazionePerGraduatoriaEnum {
    INSERT_MOVIMENTO("Passo 1:Inserimento movimento"), PROTOCOLLAZIONE_MOVIMENTO("Passo 2: protocollazione movimento"), CREAZIONE_ALLEGATO(
	    "Passo 3: Creazione allegato"), CONVERSIONE_PDF_ALLEGATO("Passo 4: Conversione in PDF dell'allegato"), FIRMA_DOCUMENTI(
	    "Passo 5: firma degli allegati generati"), INVIO_MAIL("Passo 6: Invio mail"), PROTOCOLLAZIONE_MOVIMENTO_PRIMA_CREAZIONE_ALLEGATO(
	    "Passo 7: protocollazione movimento prima creazione allegato"), ELABORAZIONE_COMPLETA("Elaborazione completa");

    private String message;

    private PassoCreazioneComunicazionePerGraduatoriaEnum(String message) {

	this.message = message;
    }
};
