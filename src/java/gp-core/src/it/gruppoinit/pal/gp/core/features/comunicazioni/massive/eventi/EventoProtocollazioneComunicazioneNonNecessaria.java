package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;

public class EventoProtocollazioneComunicazioneNonNecessaria extends EventoMassivaBase {

    private int idDettaglioComunicazione;

    public EventoProtocollazioneComunicazioneNonNecessaria(ContestoComunicazioneEnum contesto, int idDettaglioComunicazione) {

	super(contesto);
	this.idDettaglioComunicazione = idDettaglioComunicazione;
    }

    public int getIdDettaglioComunicazione() {

	return idDettaglioComunicazione;
    }
}
