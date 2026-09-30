package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TipoScadenzaGiornoDelMeseResolver implements ITipoScadenzaResolver {

    private Calendar dataPartenza;

    public TipoScadenzaGiornoDelMeseResolver(Date dataRegistrazione, int giornoDelMese) {

	if (dataRegistrazione == null) {
	    throw new IllegalArgumentException("Impossibile calcolare una scadenza periodica senza indicare la data di partenza");
	}
	if (!(giornoDelMese > 0 && giornoDelMese < 31)) {
	    throw new IllegalArgumentException("Impossibile calcolare una scadenza periodica senza indicare la data di partenza");
	}
	this.dataPartenza = GregorianCalendar.getInstance();
	this.dataPartenza.setTime(dataRegistrazione);
	int mese = this.dataPartenza.get(Calendar.MONTH);
	if (this.dataPartenza.get(Calendar.DAY_OF_MONTH) <= giornoDelMese) {
	    this.dataPartenza.set(Calendar.DATE, giornoDelMese);
	} else {
	    this.dataPartenza.set(Calendar.MONTH, mese + 1);
	    this.dataPartenza.set(Calendar.DATE, giornoDelMese);
	}
    }

    @Override
    public Date getScadenza() {

	return this.dataPartenza.getTime();
    }
}
