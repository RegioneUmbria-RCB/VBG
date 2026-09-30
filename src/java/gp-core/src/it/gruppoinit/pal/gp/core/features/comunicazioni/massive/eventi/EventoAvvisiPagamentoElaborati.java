package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;

public class EventoAvvisiPagamentoElaborati extends EventoMassivaBase {

    private int idDettaglioComunicazione;

    /**
     * @param contesto
     * @param idDettaglioComunicazione
     */
    public EventoAvvisiPagamentoElaborati(ContestoComunicazioneEnum contesto, int idDettaglioComunicazione) {

	super(contesto);
	this.idDettaglioComunicazione = idDettaglioComunicazione;
    }

    public int getIdDettaglioComunicazione() {

	return idDettaglioComunicazione;
    }
}
