package it.gruppoinit.pal.gp.core.features.attivita.denominazione;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.features.attivita.verticalizzazione.IVerticalizzazioneIAttivitaService;

public class DenominazioneResolver implements IDenominazioneResolver {

    private String nomeAttivita;

    public DenominazioneResolver(IVerticalizzazioneIAttivitaService service, IDenominazioneResolverDAO denominazioneResolverDAO,
	    String denominazioneAttuale, Istanze istanza) {

	if (service == null) {
	    throw new IllegalArgumentException("Impossibile istanziare DenominazioneResolver senza passare il service della verticalizzazione");
	}
	if (denominazioneResolverDAO == null) {
	    throw new IllegalArgumentException(
		    "Impossibile istanziare DenominazioneResolver senza passare il DAO che esegue l'eventuale query per la denominazione");
	}
	if (istanza == null) {
	    throw new IllegalArgumentException("Impossibile istanziare DenominazioneResolver senza passare l'istanza di riferimento");
	}
	if (!service.isAggiornaDenominazione() && StringUtils.isNotBlank(denominazioneAttuale)) {
	    this.nomeAttivita = denominazioneAttuale;
	}
	if (StringUtils.isBlank(this.nomeAttivita)) {
	    String query = service.getQueryDenominazione();
	    if (!StringUtils.isBlank(query)) {
		this.nomeAttivita = new DenominazioneDaVerticalizzazioneResolver(service, denominazioneResolverDAO, istanza).risolvi();
	    }
	}
	if (StringUtils.isBlank(this.nomeAttivita)) {
	    this.nomeAttivita = new DenominazioneStandardResolver(istanza).risolvi();
	}
    }

    @Override
    public String risolvi() {

	return this.nomeAttivita;
    }
}
