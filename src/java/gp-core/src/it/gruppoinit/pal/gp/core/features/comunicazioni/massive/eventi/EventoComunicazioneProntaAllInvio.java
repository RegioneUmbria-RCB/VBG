package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;

public class EventoComunicazioneProntaAllInvio extends EventoMassivaBase {

    private int idDettaglioCOmunicazione;

    protected EventoComunicazioneProntaAllInvio(ContestoComunicazioneEnum contesto, int idDettaglioCOmunicazione) {

	super(contesto);
	// TODO Auto-generated constructor stub
	this.idDettaglioCOmunicazione = idDettaglioCOmunicazione;
    }

    public int getIdDettaglioCOmunicazione() {

	return idDettaglioCOmunicazione;
    }
}
