package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import java.util.Iterator;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.CommedilizieTipopareri;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiContromovimenti;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class EsitoCommissioneEdiliziaReader implements IMetadatiReader {

    private static final String METADATO_ESITOCOMMISSIONEEDILIZIA = "esito-commissione-edilizia";
    private String esitoCommissione = null;

    public EsitoCommissioneEdiliziaReader(Movimenti movimento) {

	if (movimento != null && !movimento.getMovimentiContromovimentisForFkFiglio().isEmpty()) {
	    Set<MovimentiContromovimenti> padri = movimento.getMovimentiContromovimentisForFkFiglio();
	    for (Iterator<MovimentiContromovimenti> i = padri.iterator(); i.hasNext();) {
		MovimentiContromovimenti padre = i.next();
		Set<CommissioniedilizieR> commedilizie = padre.getMovimentoByFkPadre().getCommedilizieForFKMovimentoRientro();
		if (!commedilizie.isEmpty()) {
		    for (Iterator<CommissioniedilizieR> com = commedilizie.iterator(); com.hasNext();) {
			CommissioniedilizieR c = com.next();
			CommedilizieTipopareri ct = c.getCommedilizieTipopareri();
			if (ct != null) {
			    this.esitoCommissione = ct.getDescrizione();
			}
		    }
		}
	    }
	}
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATO_ESITOCOMMISSIONEEDILIZIA, esitoCommissione);
    }
}
