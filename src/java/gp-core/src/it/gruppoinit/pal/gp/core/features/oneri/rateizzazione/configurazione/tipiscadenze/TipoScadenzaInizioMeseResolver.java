package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TipoScadenzaInizioMeseResolver implements ITipoScadenzaResolver {

    private Calendar dataPartenza;

    public TipoScadenzaInizioMeseResolver(Date dataRegistrazione) {

	if (dataRegistrazione == null) {
	    throw new IllegalArgumentException("Impossibile calcolare una scadenza periodica senza indicare la data di partenza");
	}
	this.dataPartenza = GregorianCalendar.getInstance();
	this.dataPartenza.setTime(dataRegistrazione);
    }

    @Override
    public Date getScadenza() {

	this.dataPartenza.set(Calendar.DATE, 1);
	if (dataPartenza.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
	    // se domenica sposto la scadenza al lunedì seguente
	    dataPartenza.set(Calendar.DATE, 2);
	} else if (dataPartenza.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
	    // se sabato sposto la scadenza al lunedì seguente
	    dataPartenza.set(Calendar.DATE, 3);
	}
	return dataPartenza.getTime();
    }
}
