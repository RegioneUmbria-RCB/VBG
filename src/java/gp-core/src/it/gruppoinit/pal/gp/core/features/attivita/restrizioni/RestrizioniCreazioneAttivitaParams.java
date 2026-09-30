package it.gruppoinit.pal.gp.core.features.attivita.restrizioni;

import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Istanzestradario;

public class RestrizioniCreazioneAttivitaParams {

    String denominazioneAttivita;
    boolean checkAttive;
    Set<Istanzestradario> localizzazioni;

    public RestrizioniCreazioneAttivitaParams(String denominazioneAttivita, Set<Istanzestradario> localizzazioni, boolean checkAttive) {

	super();
	this.denominazioneAttivita = denominazioneAttivita;
	this.checkAttive = checkAttive;
	this.localizzazioni = localizzazioni;
    }

    public String getDenominazioneAttivita() {

	return denominazioneAttivita;
    }

    public boolean isCheckAttive() {

	return checkAttive;
    }

    public Set<Istanzestradario> getLocalizzazioni() {

	return localizzazioni;
    }
}
