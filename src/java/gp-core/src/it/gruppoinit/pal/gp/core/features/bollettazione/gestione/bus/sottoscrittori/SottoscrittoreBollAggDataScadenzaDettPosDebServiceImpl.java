package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.sottoscrittori;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.BollGestDettaglio;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollGestDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoDataScadenzaDettPosizioneDebitoriaModificata;
import it.gruppoinit.pal.gp.core.features.oneri.SottoscrittoreAggDataScadenzaDettPosDebServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class SottoscrittoreBollAggDataScadenzaDettPosDebServiceImpl implements IEventSubscriber<EventoDataScadenzaDettPosizioneDebitoriaModificata> {

    private static final Logger log = LoggerFactory.getLogger(SottoscrittoreAggDataScadenzaDettPosDebServiceImpl.class);
    private BollGestDettaglioDAO bollGestDettaglioDAO;

    @Autowired
    public SottoscrittoreBollAggDataScadenzaDettPosDebServiceImpl(BollGestDettaglioDAO bollGestDettaglioDAO) {

	super();
	this.bollGestDettaglioDAO = bollGestDettaglioDAO;
    }

    @Override
    public void onEvent(EventoDataScadenzaDettPosizioneDebitoriaModificata e) {

	String operazione = Utilities.generaPassword(10);
	log.debug("{} SottoscrittoreBollAggDataScadenzaDettPosDebService: {},{}",
		new Object[] { operazione, e.getIdDettPosizioneDebitoria(), e.getDataScadenza() });
	// trovo le righe di boll_gest_dettaglio
	List<BollGestDettaglio> findByIdDettPosizioneDebitoria = bollGestDettaglioDAO.findByIdDettPosizioneDebitoria(e.getIdDettPosizioneDebitoria());
	for (BollGestDettaglio bollGestDettaglio : findByIdDettPosizioneDebitoria) {
	    log.debug("{} SottoscrittoreBollAggDataScadenzaDettPosDebService: aggiorno la riga {} di da scadenza {} a {}",
		    new Object[] { operazione, bollGestDettaglio.getId().getCodice(), bollGestDettaglio.getDataScadenza(), e.getDataScadenza() });
	    bollGestDettaglio.setDataScadenza(e.getDataScadenza());
	    bollGestDettaglioDAO.update(bollGestDettaglio);
	}
    }
}
