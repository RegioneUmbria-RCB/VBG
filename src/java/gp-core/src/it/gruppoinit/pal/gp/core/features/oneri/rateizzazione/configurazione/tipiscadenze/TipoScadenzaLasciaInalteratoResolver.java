package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TipoScadenzaLasciaInalteratoResolver implements ITipoScadenzaResolver {

    private Calendar dataPartenza;

    public TipoScadenzaLasciaInalteratoResolver(Date dataRegistrazione) {

	if (dataRegistrazione == null) {
	    throw new IllegalArgumentException("Impossibile calcolare una scadenza periodica senza indicare la data di partenza");
	}
	this.dataPartenza = GregorianCalendar.getInstance();
	this.dataPartenza.setTime(dataRegistrazione);
    }

    @Override
    public Date getScadenza() {

	return this.dataPartenza.getTime();
    }
}
