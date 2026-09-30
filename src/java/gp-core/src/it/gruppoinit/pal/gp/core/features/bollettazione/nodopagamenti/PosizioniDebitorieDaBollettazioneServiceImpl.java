package it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloBollettazioneIstanzeService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloBollettazioneMercatiService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.DettaglioBollettazione;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.BollGestDettaglioService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;

@Service
public class PosizioniDebitorieDaBollettazioneServiceImpl implements PosizioniDebitorieDaBollettazioneService {

    private Logger logger = LoggerFactory.getLogger(PosizioniDebitorieDaBollettazioneServiceImpl.class);
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private BollGestDettaglioService bollGestDettaglioService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private CalcoloBollettazioneIstanzeService calcoloBollettazioneIstanzeService;
    @Autowired
    private CalcoloBollettazioneMercatiService calcoloBollettazioneMercatiService;
    @Autowired
    private NodoPagamentiService nodoPagamentiService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;

    public List<PosizioneDebitoriaBollettazioneBean> build(DettaglioBollettazione boll) {

	Boolean raggruppaPerUtenza = boll.getRaggruppaPerUtenza();
	logger.debug("build raggruppa per utenza {}", raggruppaPerUtenza);
	IPosizioniDebitorieResolver posizioniResolver;
	if (BooleanUtils.isTrue(raggruppaPerUtenza)) {
	    posizioniResolver = new PosizioniDebitorieRaggruppatePerAnagrafeResolver(verticalizzazioniService, anagrafeService,
		    bollGestDettaglioService, boll, nodoPagamentiService, comuniassociatiService);
	} else {
	    posizioniResolver = new PosizioniDebitorieNonRaggruppateResolver(verticalizzazioniService, anagrafeService, bollGestDettaglioService,
		    boll, comuniassociatiService);
	}
	logger.debug("build prima di getPosizioni");
	PosizioneDebitoriaPerNodoPagamentiBean posizioniBean = posizioniResolver.getPosizioni();
	logger.debug("build getPosizioni fatto, verifico la configurazione");
	switch (boll.getImplementazione()) {
	case ISTANZE:
	    logger.debug("build getPosizioni fatto, verifico la configurazione per istanze");
	    calcoloBollettazioneIstanzeService.validaConfigurazioneBollettazione(posizioniBean.getCodiciComune());
	    break;
	case MERCATI:
	    logger.debug("build getPosizioni fatto, verifico la configurazione per mercati");
	    calcoloBollettazioneMercatiService.validaConfigurazioneBollettazione(posizioniBean.getCodiciComune());
	    break;
	default:
	    throw new NotImplementedException("Errore calcolo bollettazione per ambito non gestito ");
	}
	return posizioniBean.getPosizioni();
    }
}
