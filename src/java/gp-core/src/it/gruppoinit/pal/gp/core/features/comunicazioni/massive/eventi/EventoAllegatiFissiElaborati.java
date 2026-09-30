package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;

public class EventoAllegatiFissiElaborati extends EventoMassivaBase {

    private int idDettaglioComunicazione;

    /**
     * @param contesto
     * @param idDettaglioComunicazione
     */
    public EventoAllegatiFissiElaborati(ContestoComunicazioneEnum contesto, int idDettaglioComunicazione) {

	super(contesto);
	this.idDettaglioComunicazione = idDettaglioComunicazione;
    }

    public int getIdDettaglioComunicazione() {

	return idDettaglioComunicazione;
    }
}
