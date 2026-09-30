package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.ComunicazioniD;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.TipoComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.TmpStatiComunicazioniD;
import it.gruppoinit.pal.gp.core.service.ComunicazioniManagerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ComunicazioniServiceHelper<E> {

    private static final Logger log = LoggerFactory.getLogger(ComunicazioniServiceHelper.class);
    private ComunicazioniManagerService comunicazioniManagerService;

    public ComunicazioniServiceHelper(ComunicazioniManagerService comunicazioniManagerService) {

	this.comunicazioniManagerService = comunicazioniManagerService;
    }

    public void elaboraComunicazione(ComunicazioniT comunicazioniT) {

	log.debug("elaboraComunicazione# start... ");
	if (validateEntityPerComunicazione(comunicazioniT)) {
	    long t1 = System.currentTimeMillis();
	    log.debug("elaboraComunicazione# INIZIALIZZAZIONE START... ");
	    comunicazioniManagerService.insertInizializzazioniStep(comunicazioniT.getId().getCodice());
	    long t2 = System.currentTimeMillis();
	    log.debug("elaboraComunicazione# INIZIALIZZAZIONE END. TIME = {}", (t2 - t1));
	    comunicazioniT.setStatoElaborazione(ComunicazioniTStatoEnum.DA_ELABORARE.value());
	    comunicazioniManagerService.updateComunicazioniT(comunicazioniT);
	    log.debug("elaboraComunicazione# INVIO COMUNICAZIONI START... ");
	    long t3 = System.currentTimeMillis();
	    inviaComunicazioni(comunicazioniT);
	    long t4 = System.currentTimeMillis();
	    log.debug("elaboraComunicazione# INVIO COMUNICAZIONI END. TIME = {} ", (t4 - t3));
	    log.debug("inviaComunicazioni# Eseguo verifica se ci sono comunicazione bloccate su invio email, ma l'email risulta inviata...");
	    boolean b = comunicazioniManagerService.exsistComunicazioniDNonTerminate(comunicazioniT.getId().getCodice());
	    if (b) {
		comunicazioniManagerService.updateStatoComunicazioneT(comunicazioniT.getId().getCodice(),
			ComunicazioniTStatoEnum.ELABORATA_CON_ERRORI);
	    } else {
		comunicazioniManagerService.updateStatoComunicazioneT(comunicazioniT.getId().getCodice(), ComunicazioniTStatoEnum.ELABORATA_OK);
	    }
	}
    }

    private void inviaComunicazioni(ComunicazioniT comunicazioniT) {

	//TipoComunicazioniT tipoComunicazioniT = comunicazioniT.getTipoComunicazioniT();
	List<TmpStatiComunicazioniD> tmpStatiComunicazioniPostElaborazione = new ArrayList<TmpStatiComunicazioniD>();
	List<ComunicazioniDHelper> list = createComunicazioniDHelper(comunicazioniT.getId().getCodice());
	for (ComunicazioniDHelper comunicazioniDHelper : list) {
	    List<TmpStatiComunicazioniD> tmpStatiComunicazioniDs = comunicazioniManagerService.findStepNonEseguiti(comunicazioniDHelper
		    .getCodiceComunicazioneD());
	    for (TmpStatiComunicazioniD tmpStatiComunicazioniD : tmpStatiComunicazioniDs) {
		log.debug("elaboraComunicazione# ESEGUI STEP. Comunicazione dettaglio = {} ", comunicazioniDHelper.getCodiceComunicazioneD());
		long t1 = System.currentTimeMillis();
		ComunicazioniD comunicazioniD = comunicazioniManagerService.findComunicazioniDById(comunicazioniDHelper.getCodiceComunicazioneD());
		Istanze istanza = null;
			
			if(comunicazioniDHelper.getCodiceIstanza()!=null)
			{
			istanza=comunicazioniManagerService.findIstanzaById(comunicazioniDHelper.getCodiceIstanza());
			}
		int i = comunicazioniManagerService.eseguiStep(tmpStatiComunicazioniD.getStato(), comunicazioniD, istanza, comunicazioniT,
			comunicazioniDHelper);
		long t2 = System.currentTimeMillis();
		log.debug("elaboraComunicazione# ESEGUI STEP Comunicazione dettaglio = {} END. Time = {} ",
			comunicazioniDHelper.getCodiceComunicazioneD(), (t2 - t1));
		if (i == 0) {
		    //		    log.error(
		    //			    "inviaComunicazioni# Comunicazione = {}, Passo = {}, Errore = {}",
		    //			    new Object[] { comunicazioniDHelper.getCodiceComunicazioneD(), tmpStatiComunicazioniD.getStato(),
		    //				    tmpStatiComunicazioniD.getEvento() });
		    break;
		}
	    }
	    tmpStatiComunicazioniPostElaborazione = comunicazioniManagerService.findStepNonEseguiti(comunicazioniDHelper.getCodiceComunicazioneD());
	    if (tmpStatiComunicazioniPostElaborazione.isEmpty()) {
		comunicazioniManagerService.updateStatoComunicazioneD(comunicazioniDHelper.getCodiceComunicazioneD(),
			ComunicazioniDStatoEnum.ELABORATA_TERMINATA);
	    } else {
		comunicazioniManagerService.updateStatoComunicazioneD(comunicazioniDHelper.getCodiceComunicazioneD(),
			ComunicazioniDStatoEnum.ELABORATA_CON_ERRORI);
	    }
	}
	comunicazioniManagerService.flush();
	comunicazioniManagerService.commit();
	comunicazioniManagerService.clear();
	//	// Problema del fatto che il sistema di invio email è asincrono alle operazione svolte in seguenza dal programma
	// ritorna un errore di transazione mentre salva comunicazionid
	//	comunicazioniManagerService.checkComunicazioniBloccateSuInvioEmail(comunicazioniT.getId().getCodice());
    }

    public ComunicazioniT inizializzaComunicazioni(List<E> list) {

	ComunicazioniT comunicazioniT = new ComunicazioniT();
	comunicazioniT.setData(new Date());
	comunicazioniT.setDescrizione("Comunicazione del " + it.gruppoinit.pal.gp.core.utils.Utilities.formatDate(new Date(), false));
	TipoComunicazioniT tipoComunicazioniT = comunicazioniManagerService.findTipoComunicazioniById(TipoComunicazioniTEnum.CONCESSIONI.value());
	comunicazioniT.setTipoComunicazioniT(tipoComunicazioniT);
	log.debug("inizializzaComunicazioni# Comunicazione prevede istanza = {}", true);
	comunicazioniT.setFlagPrevedePresenzaIstanza(true);
	log.debug("inizializzaComunicazioni# setto la comunicazione come da = {}", ComunicazioniTStatoEnum.PRE_ELABORAZIONE.toString());
	comunicazioniT.setStatoElaborazione(ComunicazioniTStatoEnum.PRE_ELABORAZIONE.value());
	comunicazioniManagerService.insertComunicazioniT(comunicazioniT);
	log.debug("inizializzaComunicazioni# Inserita comunicazioneT di tipo = {}", tipoComunicazioniT.getCodice());
	inizializzaDettaglioComunicazione(list, comunicazioniT);
	log.debug("inizializzaComunicazioni# Popolata struttura base per le comunicazioni.");
	return comunicazioniT;
    }

    protected abstract boolean validateEntityPerComunicazione(ComunicazioniT comunicazioniT);

    protected abstract void inizializzaDettaglioComunicazione(List<E> list, ComunicazioniT comunicazioniT);

    protected abstract List<ComunicazioniDHelper> createComunicazioniDHelper(Integer codicecomunicaziot);
}
