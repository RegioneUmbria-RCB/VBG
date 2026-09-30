package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TipoScadenzaQuindiciMeseSuccessivoEsclusaPrimaRataResolver implements ITipoScadenzaResolver {

    private Calendar dataPartenza;
    private int numeroRata;

    public TipoScadenzaQuindiciMeseSuccessivoEsclusaPrimaRataResolver(Date dataRegistrazione, int numeroRata) {

	if (dataRegistrazione == null) {
	    throw new IllegalArgumentException("Impossibile calcolare una scadenza periodica senza indicare la data di partenza");
	}
	this.dataPartenza = GregorianCalendar.getInstance();
	this.dataPartenza.setTime(dataRegistrazione);
	this.numeroRata = numeroRata;
	if (this.numeroRata != 0) {
	    int mese = this.dataPartenza.get(Calendar.MONTH);
	    this.dataPartenza.set(Calendar.MONTH, mese + 1);
	    this.dataPartenza.set(Calendar.DATE, 15);
	}
    }

    @Override
    public Date getScadenza() {

	return this.dataPartenza.getTime();
    }
}
