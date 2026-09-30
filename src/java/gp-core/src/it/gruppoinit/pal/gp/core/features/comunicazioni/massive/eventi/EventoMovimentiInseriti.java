package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;

public class EventoMovimentiInseriti extends EventoMassivaBase {

    private int idDettaglioComunicazione;

    public EventoMovimentiInseriti(ContestoComunicazioneEnum contesto, int idDettaglioComunicazione) {

	super(contesto);
	this.idDettaglioComunicazione = idDettaglioComunicazione;
    }

    public int getIdDettaglioComunicazione() {

	return idDettaglioComunicazione;
    }
}
