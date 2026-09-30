package it.gruppoinit.pal.gp.core.features.attivita;

import it.gruppoinit.pal.gp.core.features.attivita.denominazione.IDenominazioneResolverDAO;

public class FakeDenominazioneResolverDAO implements IDenominazioneResolverDAO {

    private String denominazione;

    public FakeDenominazioneResolverDAO(String denominazione) {

	super();
	this.denominazione = denominazione;
    }

    @Override
    public String findDenominazioneDaQuery(String queryDenominazioneStr) {

	return this.denominazione;
    }
}
