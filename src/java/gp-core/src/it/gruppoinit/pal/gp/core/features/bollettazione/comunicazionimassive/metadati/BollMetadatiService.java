package it.gruppoinit.pal.gp.core.features.bollettazione.comunicazionimassive.metadati;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.BollCfgTipoMetadatiService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.MetadatoBollettazione;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

public class BollMetadatiService implements IBollMetadatiService {

    private String segnapostoIdBollettazione = "[IDBOLLETTAZIONE]";
    private String segnapostoBollettazione = "[BOLLETTAZIONE]";
    private BollCfgTipoMetadatiService bollCfgTipoMetadatiService;

    public BollMetadatiService(BollCfgTipoMetadatiService bollCfgTipoMetadatiService) {

	this.bollCfgTipoMetadatiService = bollCfgTipoMetadatiService;
    }

    public List<MetadatoBollettazione> elencoMetadati(BollGestTestata testata) {

	if (testata == null || testata.getId() == null || testata.getId().getCodice() == null) {
	    throw new BusinessValidationException("Impossibile ottenere l'elenco dei metadati senza passare la bollettazione di riferimento");
	}
	if (testata.getBollCfgTipo() == null || testata.getBollCfgTipo().getId() == null || testata.getBollCfgTipo().getId().getCodice() == null) {
	    throw new BusinessValidationException("E' stata passata una bollettazione che non è collegata a nessuna configurazione");
	}
	if (StringUtils.isBlank(testata.getDescrizione())) {
	    throw new BusinessValidationException("E' stata passata una bollettazione senza descrizione");
	}
	Integer codiceBollcfgTipo = testata.getBollCfgTipo().getId().getCodice();
	List<MetadatoBollettazione> metadati = this.bollCfgTipoMetadatiService.elencoMetadati(codiceBollcfgTipo);
	if (metadati == null) {
	    return null;
	}
	for (MetadatoBollettazione metadato : metadati) {
	    if (StringUtils.isBlank(metadato.getValore())) {
		continue;
	    }
	    metadato.setValore(metadato.getValore().replace(this.segnapostoIdBollettazione, testata.getId().getCodice().toString()));
	    metadato.setValore(metadato.getValore().replace(this.segnapostoBollettazione, testata.getDescrizione()));
	}
	return metadati;
    }
}
