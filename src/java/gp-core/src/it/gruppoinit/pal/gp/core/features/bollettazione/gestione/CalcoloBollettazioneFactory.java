package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ImplementazioniEnum;
import it.gruppoinit.pal.gp.core.service.BollCfgTipoService;

public class CalcoloBollettazioneFactory {

    private BollCfgTipoService bollCfgTipoService;
    private CalcoloBollettazioneIstanzeService calcoloBollettazioneIstanzeService;
    private CalcoloBollettazioneMercatiService calcoloBollettazioneMercatiService;

    public CalcoloBollettazioneFactory(BollCfgTipoService bollCfgTipoService, CalcoloBollettazioneIstanzeService calcoloBollettazioneIstanzeService,
	    CalcoloBollettazioneMercatiService calcoloBollettazioneMercatiService) {

	super();
	this.bollCfgTipoService = bollCfgTipoService;
	this.calcoloBollettazioneIstanzeService = calcoloBollettazioneIstanzeService;
	this.calcoloBollettazioneMercatiService = calcoloBollettazioneMercatiService;
    }

    public Integer creaBollettazione(CreazioneBollTestata creazioneBollTestata, Integer codiceResponsabile) {

	ImplementazioniEnum tipo = bollCfgTipoService.findImplementazioneByTipo(creazioneBollTestata.getBollCfgTipoId());
	switch (tipo) {
	case ISTANZE:
	    return calcoloBollettazioneIstanzeService.creaBollettazione(creazioneBollTestata, codiceResponsabile);
	case MERCATI:
	    return calcoloBollettazioneMercatiService.creaBollettazione(creazioneBollTestata, codiceResponsabile);
	default:
	    throw new NotImplementedException("CalcoloBollettazioneFactory Implementazione [" + tipo + "] non valida");
	}
    }
}
