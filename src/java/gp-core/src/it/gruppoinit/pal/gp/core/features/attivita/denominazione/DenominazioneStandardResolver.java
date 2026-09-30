package it.gruppoinit.pal.gp.core.features.attivita.denominazione;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Istanze;

public class DenominazioneStandardResolver implements IDenominazioneResolver {

    private String nomeAttivita;

    public DenominazioneStandardResolver(Istanze istanza) {

	if (istanza == null) {
	    throw new IllegalArgumentException("Impossibile risolvere il nome dell'attività senza passare un'istanza valida");
	}
	if (StringUtils.isNotBlank(istanza.getNomeattivita())) {
	    this.nomeAttivita = istanza.getNomeattivita();
	    return;
	}
	if (istanza.getTitolarelegale() != null) {
	    this.nomeAttivita = istanza.getTitolarelegale().getNominativo() + " ";
	    return;
	}
	this.nomeAttivita = istanza.getRichiedente().getNominativo();
	if (StringUtils.isNotBlank(istanza.getRichiedente().getNome())) {
	    this.nomeAttivita += " " + istanza.getRichiedente().getNome();
	}
    }

    @Override
    public String risolvi() {

	return this.nomeAttivita.trim();
    }
}
