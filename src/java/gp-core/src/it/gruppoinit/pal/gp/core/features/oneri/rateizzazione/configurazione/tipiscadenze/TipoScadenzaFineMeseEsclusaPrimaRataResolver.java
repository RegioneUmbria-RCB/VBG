package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TipoScadenzaFineMeseEsclusaPrimaRataResolver implements ITipoScadenzaResolver {

    private Calendar dataPartenza;
    private int numeroRata;

    public TipoScadenzaFineMeseEsclusaPrimaRataResolver(Date dataRegistrazione, int numeroRata) {

	if (dataRegistrazione == null) {
	    throw new IllegalArgumentException("Impossibile calcolare una scadenza periodica senza indicare la data di partenza");
	}
	this.dataPartenza = GregorianCalendar.getInstance();
	this.dataPartenza.setTime(dataRegistrazione);
	this.numeroRata = numeroRata;
    }

    @Override
    public Date getScadenza() {

	if (this.numeroRata != 0) {
	    int giorniMese = this.dataPartenza.getActualMaximum(Calendar.DAY_OF_MONTH);
	    this.dataPartenza.set(Calendar.DATE, giorniMese);
	}
	return this.dataPartenza.getTime();
    }
}
