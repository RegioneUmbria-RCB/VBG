package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiContromovimenti;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public class DataCommissioneEdiliziaReader implements IMetadatiReader {

    private static final String METADATO_DATACOMMISSIONEEDILIZIA = "data-commissione-edilizia";
    private Date data = null;

    public DataCommissioneEdiliziaReader(Movimenti movimento) {

	if (movimento != null && !movimento.getMovimentiContromovimentisForFkFiglio().isEmpty()) {
	    Set<MovimentiContromovimenti> padri = movimento.getMovimentiContromovimentisForFkFiglio();
	    for (Iterator<MovimentiContromovimenti> i = padri.iterator(); i.hasNext();) {
		MovimentiContromovimenti padre = i.next();
		Set<CommissioniedilizieR> commedilizie = padre.getMovimentoByFkPadre().getCommedilizieForFKMovimentoRientro();
		if (!commedilizie.isEmpty()) {
		    for (Iterator<CommissioniedilizieR> com = commedilizie.iterator(); com.hasNext();) {
			CommissioniedilizieR c = com.next();
			this.data = c.getCommissioniedilizieT().getData();
		    }
		}
	    }
	}
    }

    @Override
    public DocumentiCondivisiMetadato get() {

	return new DocumentiCondivisiMetadato(METADATO_DATACOMMISSIONEEDILIZIA, data);
    }
}
