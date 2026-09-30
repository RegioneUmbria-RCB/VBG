package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;

public class EventoAllegatiDelDettaglioElaborati extends EventoMassivaBase {

    private int idDettaglioComunicazione;

    public EventoAllegatiDelDettaglioElaborati(ContestoComunicazioneEnum contesto, int idDettaglioComunicazione) {

	super(contesto);
	this.idDettaglioComunicazione = idDettaglioComunicazione;
    }

    public int getIdDettaglioComunicazione() {

	return idDettaglioComunicazione;
    }
}
