package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TipoScadenzaQuindiciMeseResolver implements ITipoScadenzaResolver {

    private Calendar dataPartenza;

    public TipoScadenzaQuindiciMeseResolver(Date dataRegistrazione) {

	if (dataRegistrazione == null) {
	    throw new IllegalArgumentException("Impossibile calcolare una scadenza periodica senza indicare la data di partenza");
	}
	this.dataPartenza = GregorianCalendar.getInstance();
	this.dataPartenza.setTime(dataRegistrazione);
	int mese = this.dataPartenza.get(Calendar.MONTH);
	if (this.dataPartenza.get(Calendar.DAY_OF_MONTH) <= 15) {
	    this.dataPartenza.set(Calendar.DATE, 15);
	} else {
	    this.dataPartenza.set(Calendar.MONTH, mese + 1);
	    this.dataPartenza.set(Calendar.DATE, 15);
	}
    }

    @Override
    public Date getScadenza() {

	return this.dataPartenza.getTime();
    }
}
