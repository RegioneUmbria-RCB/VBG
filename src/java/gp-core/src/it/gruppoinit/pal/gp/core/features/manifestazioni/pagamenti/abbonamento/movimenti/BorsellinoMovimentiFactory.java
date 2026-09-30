package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti;

import java.math.BigDecimal;

import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimentiImporti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.RigaImporto;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.AbbonamentoTabellaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IBorsellinoDAO;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;

public class BorsellinoMovimentiFactory {

    private IBorsellinoDAO borsellinoDAO;
    private ContiService contiService;

    public BorsellinoMovimentiFactory(ContiService contiService, IBorsellinoDAO borsellinoDAO) {

	this.contiService = contiService;
	this.borsellinoDAO = borsellinoDAO;
    }

    public BorsellinoMovimenti buildUscita(BorsellinoMovimentiHelper helper) {

	Borsellino borsellino = this.borsellinoDAO.findById(new PkId(helper.getIdBorsellino()));
	BorsellinoMovimenti movimento = BorsellinoMovimenti.movimentoUscita(borsellino,
		helper.getGiornata().getMercatiPresenzeT(), helper.getGiornata().getAutorizzazioni(), helper.getGiornata().getPosteggio(),
		helper.getImporto());
	for (RigaImporto riga : helper.getImporti()) {
	    BorsellinoMovimentiImporti importo = new BorsellinoMovimentiImporti();
	    importo.setBorsellinoMovimenti(movimento);
	    if (riga.getConto() != null) {
		importo.setConto(this.contiService.findById(riga.getConto().getId()));
	    }
	    importo.setImporto(riga.getImporto());
	    movimento.getImporti().add(importo);
	}
	
	BigDecimal saldototale;
	if(borsellino.getSaldoTotale() == null){
	    //Mi affido a questo come saldo
	    BigDecimal saldo = AbbonamentoTabellaModel.fromBorsellino(borsellino).getCreditoResiduo();
	    movimento.setCreditoIniziale(saldo);
	    
	    saldototale = saldo.add(movimento.getImporto());
	    movimento.setCreditoFinale(saldototale);
	}else{
	    movimento.setCreditoIniziale(borsellino.getSaldoTotale());
	    
	    saldototale = borsellino.getSaldoTotale().add(movimento.getImporto());
	    movimento.setCreditoFinale(saldototale);
	}	
	
	return movimento;
    }
}
