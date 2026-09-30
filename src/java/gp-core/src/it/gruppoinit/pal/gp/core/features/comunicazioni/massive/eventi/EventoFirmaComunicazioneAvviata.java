package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;

public class EventoFirmaComunicazioneAvviata extends EventoMassivaBase {

    private int idDettaglioComunicazione;

    public EventoFirmaComunicazioneAvviata(ContestoComunicazioneEnum contesto, int idDettaglioComunicazione) {

	super(contesto);
	this.idDettaglioComunicazione = idDettaglioComunicazione;
    }

    public int getIdDettaglioComunicazione() {

	return idDettaglioComunicazione;
    }
}