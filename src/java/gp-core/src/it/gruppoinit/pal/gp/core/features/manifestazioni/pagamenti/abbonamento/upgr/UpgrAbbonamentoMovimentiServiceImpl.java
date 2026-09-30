package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.upgr;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.AbbonamentoTabellaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.BorsellinoMovimentiLight;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IBorsellinoDAO;

@Service
public class UpgrAbbonamentoMovimentiServiceImpl implements IUpgrAbbonamentoMovimentiService{
    
    private static final Logger log = LoggerFactory.getLogger(UpgrAbbonamentoMovimentiServiceImpl.class);
    
    @Autowired
    private UpgrAbbonamentoMovimentiDAO upgrAbbonamentoMovimentiDAO;
    @Autowired
    private IBorsellinoDAO borsellinoDAO;
    
    
    @Override
    public void aggiornaMovimentiAbbonamento(){
	
	log.debug("Sto estraendo i borsellini su cui fare upgr");
	List<Object[]> borsellini = upgrAbbonamentoMovimentiDAO.findAllBorsellini();
	log.debug("Borsellini estratti: {}", borsellini.size());
	
	int borsellinisize = borsellini.size();
	int count = 0;
	log.debug("Faccio upgr del borsellino e dei suoi movimenti");
	for(Object[] riga : borsellini){
	    count++;
	    log.debug("Riga numero {} di {}", count, borsellinisize);
	    
	    String idcomune = (String)riga[0];
	    Integer idborsellino = (Integer)riga[1];
	    
	    log.debug("Upgr su borsellino con id ({}, {})", idcomune, idborsellino);
	    
	    Borsellino borsellino = borsellinoDAO.findById(new PkId(idcomune, idborsellino));
	    Set<BorsellinoMovimenti> borsellinoMovimenti = borsellino.getBorsellinoMovimenti();
	    List<BorsellinoMovimentiLight> listaMovimenti = new ArrayList<BorsellinoMovimentiLight>();
	    BigDecimal credito = new BigDecimal(0);
	    for (BorsellinoMovimenti bm : borsellinoMovimenti) {
		    if (AbbonamentoTabellaModel.verificaCreditoDelMovimento(bm)) {
			credito = credito.add(bm.getImporto());
			
			/*
			 * A differenza della logica che mostra i movimenti, qui siamo rimasti d accordo
			 * che i movimenti sui quali non risulta una pd pagata non verranno aggiornati
			 * dunque listaMovimenti.add deve entrare in questo if
			 */
			BorsellinoMovimentiLight movimentoLight = new BorsellinoMovimentiLight();
			movimentoLight.setId(bm.getId().getCodice());
			movimentoLight.setImporto(bm.getImporto());
			movimentoLight.setDatamovimento(bm.getData());
			listaMovimenti.add(movimentoLight);
		    }else{
			/*
			 * Per sicurezza settiamo a null in caso di un ulteriore giro
			 */
			upgrAbbonamentoMovimentiDAO.updateCreditiMovimento(idcomune, bm.getId().getCodice(), null, null);
		    }
	    }
	    
	    calcolaCreditiByBorsellino(idcomune, listaMovimenti);
	    
	    borsellino.setSaldoTotale(credito);
	    borsellinoDAO.update(borsellino);
	    borsellinoDAO.flush();
	    borsellinoDAO.commit();
	    
	    log.debug("Upgr effettuato");
	}
	
    }
    
    private void calcolaCreditiByBorsellino(String idcomune, List<BorsellinoMovimentiLight> listaMovimenti){
	
	Collections.sort(listaMovimenti, Collections.reverseOrder());
	
	BigDecimal creditoMovimento = new BigDecimal(0);
	for(BorsellinoMovimentiLight movimentoLight : listaMovimenti){
	    upgrAbbonamentoMovimentiDAO.updateCreditiMovimento(idcomune, movimentoLight.getId(), 
		    creditoMovimento, creditoMovimento.add(movimentoLight.getImporto()));
	    creditoMovimento = creditoMovimento.add(movimentoLight.getImporto());
	}
    }
    
    
    
}
