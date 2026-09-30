package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaDAO;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;

@Component
public class NodoPagamentiManagerImpl implements NodoPagamentiManager {

    private static Logger log = LoggerFactory.getLogger(NodoPagamentiManagerImpl.class);
    private NodoPagamentiService nodoPagamentiService;
    private DettPosizioneDebitoriaDAO dettposizionedebitoriaDAO;

    @Autowired
    public void setDettposizionedebitoriaDAO(DettPosizioneDebitoriaDAO dettposizionedebitoriaDAO) {

	this.dettposizionedebitoriaDAO = dettposizionedebitoriaDAO;
    }

    @Autowired
    public void setNodoPagamentiService(NodoPagamentiService nodoPagamentiService) {

	this.nodoPagamentiService = nodoPagamentiService;
    }

    @Override
    public void aggiornaStatoPosizioniDebitorieInStati(String[] statiDaVerificareArr) {

	Set<Integer> codici = dettposizionedebitoriaDAO.findIdDettaglioByListaStati(statiDaVerificareArr);
	LoggerUpdaterecord.log(
		"##VERIFICA STATO POSIZIONI START## ELABORO ALIAS " + ORMHelper.getIdcomuneAlias() + ", n# posizioni da elaborare " + codici.size());
	for (Integer idDettaglioPosizioneDebitoria : codici) {
	    log.debug("idDettaglioPosizioneDebitoria {}-{}, ", ORMHelper.getIdcomuneAlias(), idDettaglioPosizioneDebitoria);
	    try {
		DettPosizioneDebitoria dett = dettposizionedebitoriaDAO.findById(new PkId(idDettaglioPosizioneDebitoria));
		if (dett != null && StringUtils.isNotBlank(dett.getCodiceSoftware())) {
		    ORMHelper.setSoftware(dett.getCodiceSoftware());
		    log.debug("idDettaglioPosizioneDebitoria Setto il software {}-{}, ", ORMHelper.getSoftware(), idDettaglioPosizioneDebitoria);
		}
		this.nodoPagamentiService.aggiornaStatoPagamentoByIdDettPosizioneDebitoria(idDettaglioPosizioneDebitoria);
	    } catch (FunzioneBusinessRemotaException e) {
		log.error("aggiornaStatoPosizioniDebitorieInStati# errore nell'aggiornamento della posizione debitoria: {}",
			idDettaglioPosizioneDebitoria, e);
	    } catch (Exception e) {
		log.error("aggiornaStatoPosizioniDebitorieInStati# errore nell'aggiornamento della posizione debitoria: {}",
			idDettaglioPosizioneDebitoria, e);
	    }
	}
    }
}
