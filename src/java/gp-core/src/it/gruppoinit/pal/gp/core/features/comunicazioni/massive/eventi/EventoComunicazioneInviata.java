package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi;

import java.util.List;

public class EventoComunicazioneInviata extends EventoMassivaBase {

    private int idDettaglioComunicazione;
    private List<String> warnings;

    public EventoComunicazioneInviata(ContestoComunicazioneEnum contesto, int idDettaglioComunicazione) {

	super(contesto);
	this.idDettaglioComunicazione = idDettaglioComunicazione;
    }

    public int getIdDettaglioComunicazione() {

	return idDettaglioComunicazione;
    }

    
    public List<String> getWarnings() {
    
        return warnings;
    }

    
    public void setWarnings(List<String> warnings) {
    
        this.warnings = warnings;
    }
    
}
