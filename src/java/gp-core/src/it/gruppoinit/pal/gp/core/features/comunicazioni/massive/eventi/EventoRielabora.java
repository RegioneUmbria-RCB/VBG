package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;


public class EventoRielabora extends EventoMassivaBase {

    private int idDettaglioComunicazione;

    public EventoRielabora(ContestoComunicazioneEnum contesto, int idDettaglioComunicazione) {

	super(contesto);
	this.idDettaglioComunicazione = idDettaglioComunicazione;
    }

    public int getIdDettaglioComunicazione() {

	return idDettaglioComunicazione;
    }

}
