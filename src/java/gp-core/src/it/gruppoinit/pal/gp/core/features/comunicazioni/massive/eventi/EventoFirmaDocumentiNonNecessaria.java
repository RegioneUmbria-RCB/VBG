package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;

public class EventoFirmaDocumentiNonNecessaria extends EventoMassivaBase {

    private int idDettaglioComunicazione;

    public EventoFirmaDocumentiNonNecessaria(ContestoComunicazioneEnum contesto, int idDettaglioComunicazione) {

	super(contesto);
	this.idDettaglioComunicazione = idDettaglioComunicazione;
    }

    public int getIdDettaglioComunicazione() {

	return idDettaglioComunicazione;
    }
}
