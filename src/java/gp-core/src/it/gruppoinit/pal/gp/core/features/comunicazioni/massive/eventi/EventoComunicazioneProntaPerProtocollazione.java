package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;

public class EventoComunicazioneProntaPerProtocollazione extends EventoMassivaBase {

    private int idDettaglioComunicazione;

    protected EventoComunicazioneProntaPerProtocollazione(ContestoComunicazioneEnum contesto, int idDettaglioComunicazione) {

	super(contesto);
	// TODO Auto-generated constructor stub
	this.idDettaglioComunicazione = idDettaglioComunicazione;
    }

    public int getIdDettaglioComunicazione() {

	return idDettaglioComunicazione;
    }
}
