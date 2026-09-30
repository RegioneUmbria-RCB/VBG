package it.gruppoinit.pal.gp.core.features.osservatorio.fvg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.osservatorio.IVerticalizzazioneOsservatorioRegionaleService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

@Service
public class VerticalizzazioneOsservatorioRegionaleFVGServiceImpl implements IVerticalizzazioneOsservatorioRegionaleFVGService {

    @Autowired
    private VerticalizzazioniService service;
    @Autowired
    private IVerticalizzazioneOsservatorioRegionaleService verticalizzazioneOsservatorioRegionaleService;
    private static final String NOME_VERTICALIZZAZIONE = "OSSERVATORIO_FVG";

    @Override
    public boolean isAttiva() {

	if (!this.verticalizzazioneOsservatorioRegionaleService.isAttiva()
		|| !VerticalizzazioneOsservatorioRegionaleFVGServiceImpl.NOME_VERTICALIZZAZIONE
			.equals(this.verticalizzazioneOsservatorioRegionaleService.getServizioAttivo())) {
	    return false;
	}
	return this.service.isAttiva(VerticalizzazioneOsservatorioRegionaleFVGServiceImpl.NOME_VERTICALIZZAZIONE);
    }
}
