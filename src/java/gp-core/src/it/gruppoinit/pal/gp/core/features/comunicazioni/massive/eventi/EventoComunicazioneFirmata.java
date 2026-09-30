package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;

public class EventoComunicazioneFirmata extends EventoMassivaBase {

    private int idDettaglioComunicazione;

    protected EventoComunicazioneFirmata(ContestoComunicazioneEnum contesto, int idDettaglioComunicazione) {

	super(contesto);
	this.idDettaglioComunicazione = idDettaglioComunicazione;
    }

    public int getIdDettaglioComunicazione() {

	return idDettaglioComunicazione;
    }
}
