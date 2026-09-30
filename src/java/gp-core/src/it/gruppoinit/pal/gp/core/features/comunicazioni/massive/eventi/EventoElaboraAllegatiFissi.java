package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;

public class EventoElaboraAllegatiFissi extends EventoMassivaBase {

    private int idDettaglioComunicazione;

    public EventoElaboraAllegatiFissi(ContestoComunicazioneEnum contesto, int idDettaglioComunicazione) {

	super(contesto);
	this.idDettaglioComunicazione = idDettaglioComunicazione;
    }

    public int getIdDettaglioComunicazione() {

	return idDettaglioComunicazione;
    }
}
