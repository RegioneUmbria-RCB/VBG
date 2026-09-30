package it.gruppoinit.pal.gp.core.features.autorizzazioni.auditing;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;

public class ModificaOccupanteLogger extends AbstractAutorizzazioniLogger {

    public ModificaOccupanteLogger(String autore, Autorizzazioni aut, Anagrafe precedenteOccupante, Anagrafe nuovoOccupante) {

	super(autore);
	this.messaggio = this.generaMessaggio(aut, precedenteOccupante, nuovoOccupante);
    }

    private String generaMessaggio(Autorizzazioni aut, Anagrafe precedenteOccupante, Anagrafe nuovoOccupante) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("MODIFICA OCCUPANTE AUTORIZZAZIONE");
	sb.append("\nAUTORIZZAZIONE: ").append(aut.getTransientEstremiAut());
	sb.append("\nID: ").append(aut.getId());
	sb.append("\nNUOVO OCCUPANTE: ").append(nuovoOccupante.getDescrizioneRichiedente());
	sb.append("\nPRECEDENTE OCCUPANTE: ").append(precedenteOccupante.getDescrizioneRichiedente());
	sb.append("\n").append(DELIMITATORE_LOG);
	return sb.toString();
    }
}
