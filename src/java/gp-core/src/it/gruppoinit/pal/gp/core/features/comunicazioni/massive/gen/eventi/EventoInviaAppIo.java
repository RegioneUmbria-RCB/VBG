package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.eventi;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoMassivaBase;

public class EventoInviaAppIo extends EventoMassivaBase {

    private int idDettaglioComunicazione;
    private List<String> warnings;

    public EventoInviaAppIo(ContestoComunicazioneEnum contesto, int idDettaglioComunicazione) {

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
