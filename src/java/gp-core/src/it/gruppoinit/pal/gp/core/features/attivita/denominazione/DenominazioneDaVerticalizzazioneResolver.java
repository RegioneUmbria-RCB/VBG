package it.gruppoinit.pal.gp.core.features.attivita.denominazione;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.features.attivita.verticalizzazione.IVerticalizzazioneIAttivitaService;

public class DenominazioneDaVerticalizzazioneResolver implements IDenominazioneResolver {

    private String nomeAttivita;

    public DenominazioneDaVerticalizzazioneResolver(IVerticalizzazioneIAttivitaService service, IDenominazioneResolverDAO denominazioneResolverDAO,
	    Istanze istanza) {

	if (service == null) {
	    throw new IllegalArgumentException(
		    "Impossibile risolvere la denominazione dell'attività in quanto non è stato passato il service che gestisce la verticalizzazione");
	}
	if (istanza == null || istanza.getId() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile risolvere la denominazione dell'attività in quanto non è stata passata un'istanza valida");
	}
	String queryDenominazione = service.getQueryDenominazione();
	if (StringUtils.isEmpty(queryDenominazione)) {
	    throw new RuntimeException("Impossibile risolvere il nome dell'attività dalla verticalizzazione perchè il parametro è vuoto!");
	}
	queryDenominazione = queryDenominazione.replace("&CODICEISTANZA", String.valueOf(istanza.getId().getCodice())).replace("&IDCOMUNE",
		ORMHelper.getIdcomune());
	this.nomeAttivita = denominazioneResolverDAO.findDenominazioneDaQuery(queryDenominazione);
    }

    @Override
    public String risolvi() {

	return this.nomeAttivita.trim();
    }
}
