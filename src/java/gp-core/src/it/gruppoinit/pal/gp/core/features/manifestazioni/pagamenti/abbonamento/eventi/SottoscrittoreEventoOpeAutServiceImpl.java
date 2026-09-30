package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAutStorico;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAutorizzazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IBorsellinoAutStoricoDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IBorsellinoAutorizzazioniDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IBorsellinoDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.BorsellinoException;

@Service
public class SottoscrittoreEventoOpeAutServiceImpl implements IEventSubscriber<EventoOperazioneAutorizzazione> {

    private static final Logger logger = LoggerFactory.getLogger(SottoscrittoreEventoOpeAutServiceImpl.class);
    
    @Autowired
    private IBorsellinoAutStoricoDAO borsellinoAutStoricoDAO;
    @Autowired
    private IBorsellinoAutorizzazioniDAO borsellinoAutorizzazioniDAO;
    @Autowired
    private IBorsellinoDAO borsellinoDAO;

    @Override
    public void onEvent(EventoOperazioneAutorizzazione e) throws EventAbortedException {

	List<Integer> idsborsellino = new ArrayList<Integer>();
	if(e.getIdborsellino() != null){
	    idsborsellino.add(e.getIdborsellino()); 
	}else if(e.getUuidBorsellino() != null){
	    try {
		idsborsellino.add(borsellinoDAO.findByUuid(e.getUuidBorsellino()).getId().getCodice());
	    } catch (BorsellinoException e1) {
		logger.error("Nessun borsellino trovato");
	    }
	}else{
	    List<BorsellinoAutorizzazioni> borsellinoautorizzazioni = borsellinoAutorizzazioniDAO.findByIdAutorizzazione(e.getIdautorizzazione());
	    for(BorsellinoAutorizzazioni ba : borsellinoautorizzazioni){
		idsborsellino.add(ba.getId().getFkIdBorsellino());
	    }
	}
	
	
	for(Integer idborsellino : idsborsellino){
	    logger.debug("Sto inserendo evento {} in borsellino_aut_storico con borsellinoId {} e autorizzazioneId {} ", new Object[] { e.getTipoOperazione().name(), idborsellino, e.getIdautorizzazione() });	
		BorsellinoAutStorico borsellinoAutStorico = new BorsellinoAutStorico();
		borsellinoAutStorico.setId(new PkId());
		borsellinoAutStorico.setFkidBorsellino(idborsellino);
		borsellinoAutStorico.setFkidAutorizzazioni(e.getIdautorizzazione());
		borsellinoAutStorico.setTipoOperazione(e.getTipoOperazione().name());
		borsellinoAutStorico.setDataOperazione(new Date());
		borsellinoAutStoricoDAO.insert(borsellinoAutStorico);
		logger.debug("Ho inserito evento {} in borsellino_aut_storico con borsellinoId {} e autorizzazioneId {} ", new Object[] { e.getTipoOperazione().name(), idborsellino, e.getIdautorizzazione() });
	}		

    }
}
