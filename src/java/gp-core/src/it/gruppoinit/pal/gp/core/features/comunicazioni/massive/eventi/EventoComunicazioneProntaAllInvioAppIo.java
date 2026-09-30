package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;

import java.util.List;

public class EventoComunicazioneProntaAllInvioAppIo extends EventoMassivaBase {

    private int idDettaglioCOmunicazione;
    private List<String> warnings;

    public EventoComunicazioneProntaAllInvioAppIo(ContestoComunicazioneEnum contesto, int idDettaglioCOmunicazione) {

	super(contesto);
	// TODO Auto-generated constructor stub
	this.idDettaglioCOmunicazione = idDettaglioCOmunicazione;
    }

    public int getIdDettaglioCOmunicazione() {

	return idDettaglioCOmunicazione;
    }

    
    public List<String> getWarnings() {
    
        return warnings;
    }

    
    public void setWarnings(List<String> warnings) {
    
        this.warnings = warnings;
    }
    
    
}
