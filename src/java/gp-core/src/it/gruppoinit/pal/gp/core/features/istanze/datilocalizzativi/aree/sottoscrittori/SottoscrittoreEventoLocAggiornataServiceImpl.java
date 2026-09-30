package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.sottoscrittori;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.RicalcoloAreeDAO;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.RicalcoloAreeIstanzeDAO;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.eventi.EventoLocalizzazioneIstanzaUpd;

@Service
public class SottoscrittoreEventoLocAggiornataServiceImpl implements IEventSubscriber<EventoLocalizzazioneIstanzaUpd> {

    private static final Logger logger = LoggerFactory.getLogger(SottoscrittoreEventoLocAggiornataServiceImpl.class);
    @Autowired
    RicalcoloAreeDAO ricalcoloAreeDAO;
    @Autowired
    RicalcoloAreeIstanzeDAO ricalcoloAreeIstanzeDAO;

    @Override
    public void onEvent(EventoLocalizzazioneIstanzaUpd e) throws EventAbortedException {

	if (ricalcoloAreeIstanzeDAO.existsRicalcoloInProgressIst(e.getUuidistanza())) {
	    logger.debug("Istanza già inserita nel ricalcolo aree");
	    return;
	}
	//1. Creo la testata
	String idRicalcolo = this.ricalcoloAreeDAO.creaRicalcoloAreeIstanza();
	//2. Aggiungo l'istanza
	this.ricalcoloAreeIstanzeDAO.aggiungiIstanzaARicalcolo(idRicalcolo, e.getUuidistanza());
    }
}
