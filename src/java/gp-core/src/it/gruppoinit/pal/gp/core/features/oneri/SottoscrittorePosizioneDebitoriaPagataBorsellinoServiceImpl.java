package it.gruppoinit.pal.gp.core.features.oneri;

import java.math.BigDecimal;

import java.util.List;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.AbbonamentoTabellaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IBorsellinoDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.IBorsellinoMovimentiDAO;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoPosizioneDebitoriaPagata;

@Service
public class SottoscrittorePosizioneDebitoriaPagataBorsellinoServiceImpl implements IEventSubscriber<EventoPosizioneDebitoriaPagata> {

    private static final Logger log = LoggerFactory.getLogger(SottoscrittorePosizioneDebitoriaPagataBorsellinoServiceImpl.class);
    
    private IBorsellinoDAO borsellinoDAO;
    private IBorsellinoMovimentiDAO borsellinoMovimentiDAO;
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;

    @Autowired
    public SottoscrittorePosizioneDebitoriaPagataBorsellinoServiceImpl(IBorsellinoDAO borsellinoDAO, IBorsellinoMovimentiDAO borsellinoMovimentiDAO, DettPosizioneDebitoriaService dettPosizioneDebitoriaService) {

	this.borsellinoDAO = borsellinoDAO;
	this.borsellinoMovimentiDAO = borsellinoMovimentiDAO;
	this.dettPosizioneDebitoriaService = dettPosizioneDebitoriaService;
    }

    @Override
    public void onEvent(EventoPosizioneDebitoriaPagata e) {

	try{
	    
	    Integer iddettposizionedebitoria = null;
	    
	    if(e.getDatiPagamento() != null){
		DettPosizioneDebitoria dett = dettPosizioneDebitoriaService.findByFkIdPosizioneDebitoriaAndCfEnteCreditore(e.getDatiPagamento().getIdPosizioneDebitoria(), e.getCfEnteCreditore());
		if(dett != null){
		    iddettposizionedebitoria = dett.getId().getCodice();
		}
	    }
	    
	    log.debug("Iddettposizionedebitoria trovata: {} ", iddettposizionedebitoria);
	    
	    if (iddettposizionedebitoria == null) {
		log.debug("Nessun id trovato per la posizione debitoria pagata");
		return;
	    }
	    List<BorsellinoMovimenti> movimenti = borsellinoMovimentiDAO.findByPosizioneDebitoria(iddettposizionedebitoria);
	    if (movimenti == null || movimenti.isEmpty()) {
		log.debug("Non sono stati trovati movimenti borsellino per la posizione pagata {}", iddettposizionedebitoria);
		return;
	    }
	    for (BorsellinoMovimenti bm : movimenti) {
		
		if(bm.getCreditoIniziale() != null){
		    log.debug("credito iniziale e finale gia valorizzato per la posizione {}" , iddettposizionedebitoria);
		    continue;
		}
		
		Borsellino borsellino = bm.getBorsellino();
		BigDecimal saldo;
		if (borsellino.getSaldoTotale() == null) {
		    //probabilmente questo blocco non serve
		    saldo = AbbonamentoTabellaModel.fromBorsellino(borsellino).getCreditoResiduo();
		    if (saldo == null) {
			saldo = new BigDecimal(0);
		    }
		} else {
		    saldo = borsellino.getSaldoTotale();
		}
		bm.setCreditoIniziale(saldo);
		bm.setCreditoFinale(saldo.add(bm.getImporto()));
		bm.setData(new java.util.Date());
		borsellinoMovimentiDAO.saveEntity(bm);
		borsellino.setSaldoTotale(saldo.add(bm.getImporto()));
		borsellinoDAO.saveEntity(borsellino);		
	    }
	}catch(Exception ex){
	    log.error("Errore durante il SottoscrittorePosizioneDebitoriaPagataBorsellinoServiceImpl", ex);
	}		
    }
    
}
