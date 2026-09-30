package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;

/**
 * <pre>
 * @author gianpaolot
 * 
 * La classe è utilizza come filto per la ricerca
 * delle istanze da utilizzare per creare un attivita.
 * 
 * <pre>
 */
public class IstanzePerAttivitaFilter {

    private Azioni azioni;
    private Boolean flagLocalizzazione;
    private Boolean flagIgnoraEsistenza;
    private Comuni comuni;
    private String software;

    public IstanzePerAttivitaFilter() {

	this.azioni = new Azioni();
	this.flagIgnoraEsistenza = Boolean.valueOf(false);
	this.flagLocalizzazione = Boolean.valueOf(false);
	this.comuni = new Comuni();
    }

    public Azioni getAzioni() {

	return azioni;
    }

    public void setAzioni(Azioni azioni) {

	this.azioni = azioni;
    }

    public Boolean getFlagLocalizzazione() {

	return flagLocalizzazione;
    }

    public void setFlagLocalizzazione(Boolean flagLocalizzazione) {

	this.flagLocalizzazione = flagLocalizzazione;
    }

    public Boolean getFlagIgnoraEsistenza() {

	return flagIgnoraEsistenza;
    }

    public void setFlagIgnoraEsistenza(Boolean flagIgnoraEsistenza) {

	this.flagIgnoraEsistenza = flagIgnoraEsistenza;
    }

    public Comuni getComuni() {

	return comuni;
    }

    public void setComuni(Comuni comuni) {

	this.comuni = comuni;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }
}
