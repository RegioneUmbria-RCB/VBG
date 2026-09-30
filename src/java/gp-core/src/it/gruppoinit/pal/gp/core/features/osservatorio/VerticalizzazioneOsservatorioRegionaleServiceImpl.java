package it.gruppoinit.pal.gp.core.features.osservatorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

@Service
public class VerticalizzazioneOsservatorioRegionaleServiceImpl implements IVerticalizzazioneOsservatorioRegionaleService {

    @Autowired
    private VerticalizzazioniService service;
    private static final String NOME_VERTICALIZZAZIONE = "OSSERVATORIO_REGIONALE";
    private static final String PAR_SERVIZIO_ATTIVO = "SERVIZIO_ATTIVO";

    @Override
    public boolean isAttiva() {

	return this.service.isAttiva(VerticalizzazioneOsservatorioRegionaleServiceImpl.NOME_VERTICALIZZAZIONE);
    }

    @Override
    public String getServizioAttivo() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneOsservatorioRegionaleServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneOsservatorioRegionaleServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneOsservatorioRegionaleServiceImpl.PAR_SERVIZIO_ATTIVO);
    }
}
