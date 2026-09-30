package it.gruppoinit.pal.gp.core.dao.helper;

public enum PassoCreazioneComunicazionePerGraduatoriaEnum {
    INSERT_MOVIMENTO("Passo 1:Inserimento movimento"), PROTOCOLLAZIONE_MOVIMENTO("Passo 2: protocollazione movimento"), CREAZIONE_ALLEGATO(
	    "Passo 3: Creazione allegato"), FIRMA_DOCUMENTI("Passo 4: firma degli allegati generati"), INVIO_MAIL("Passo 5: Invio mail"), ELABORAZIONE_COMPLETA(
	    "Elaborazione completa");

    private String message;

    private PassoCreazioneComunicazionePerGraduatoriaEnum(String message) {

	this.message = message;
    }
};
