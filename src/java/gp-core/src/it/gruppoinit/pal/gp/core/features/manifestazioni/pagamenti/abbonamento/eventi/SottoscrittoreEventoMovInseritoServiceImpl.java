package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IBorsellinoDAO;

@Service
public class SottoscrittoreEventoMovInseritoServiceImpl implements IEventSubscriber<EventoMovimentoInserito> {

    private static final Logger logger = LoggerFactory.getLogger(SottoscrittoreEventoMovInseritoServiceImpl.class);
    
    @Autowired
    private IBorsellinoDAO borsellinoDAO;

    @Override
    public void onEvent(EventoMovimentoInserito e) throws EventAbortedException {

	logger.debug("Sto aggiornando il saldo totale su borsellino id {}", e.getIdborsellino());	
	Borsellino borsellino = borsellinoDAO.findById(new PkId(e.getIdborsellino()));
	borsellino.setSaldoTotale(e.getCreditoTotale());
	borsellinoDAO.saveEntity(borsellino);
	borsellinoDAO.commit();
	logger.debug("Ho aggiornato il saldo totale su borsellino id {}", e.getIdborsellino());

    }
}
