package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TipoScadenzaFineMeseSuccessivoResolver implements ITipoScadenzaResolver {

    private Calendar dataPartenza;

    public TipoScadenzaFineMeseSuccessivoResolver(Date dataRegistrazione) {

	if (dataRegistrazione == null) {
	    throw new IllegalArgumentException("Impossibile calcolare una scadenza periodica senza indicare la data di partenza");
	}
	this.dataPartenza = GregorianCalendar.getInstance();
	this.dataPartenza.setTime(dataRegistrazione);
	dataPartenza.add(Calendar.MONTH, 1);
	dataPartenza.set(Calendar.DAY_OF_MONTH, dataPartenza.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    @Override
    public Date getScadenza() {

	return dataPartenza.getTime();
    }
}
