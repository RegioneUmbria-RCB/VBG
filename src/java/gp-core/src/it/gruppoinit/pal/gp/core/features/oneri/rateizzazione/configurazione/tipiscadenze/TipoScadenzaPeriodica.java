package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.utils.Utilities;

public class TipoScadenzaPeriodica implements ITipoScadenzaResolver {

    private static final Logger logger = LoggerFactory.getLogger(TipoScadenzaPeriodica.class);
    private String periodo;
    private Calendar dataPartenza;
    private String dateFormat = "dd/MM/yyyy";
    private int numeroRata;

    public TipoScadenzaPeriodica(Date dataPartenza, String periodo, int numeroRata) {

	if (dataPartenza == null) {
	    throw new IllegalArgumentException("Impossibile calcolare una scadenza periodica senza indicare la data di partenza");
	}
	if (StringUtils.isBlank(periodo)) {
	    throw new IllegalArgumentException("Impossibile calcolare una scadenza periodica senza indicare il periodo di riferimento");
	}
	this.dataPartenza = GregorianCalendar.getInstance();
	this.dataPartenza.setTime(dataPartenza);
	this.periodo = periodo;
	this.numeroRata = numeroRata;
    }

    @Override
    public Date getScadenza() {

	// le scadenze vengono indicate nel formato 26/07;12/09.... 
	String[] periodi = this.periodo.split(";");
	String[] rif = periodi[this.numeroRata].split("/");
	Calendar dataRiferimento = GregorianCalendar.getInstance();
	if (rif.length == 2) {
	    int gg = Integer.parseInt(rif[0]);
	    int mm = Integer.parseInt(rif[1]) - 1;
	    String data = periodi[0] + "/" + dataPartenza.get(Calendar.YEAR);
	    logger.debug("data: {}", data);
	    if (!this.isValid(data)) {
		throw new IllegalArgumentException(
			"Errore in fase di configurazione. La data di scadenza dovrebbe essere " + data + " e non è una data valida!");
	    }
	    dataRiferimento.set(dataPartenza.get(Calendar.YEAR), mm, gg);
	    logger.debug("dataRiferimento: {}, dataPartenza: {}", dataRiferimento, dataPartenza);
	    if (Utilities.compareDates(dataRiferimento.getTime(), dataPartenza.getTime()) >= 0) {
		return dataRiferimento.getTime();
	    }
	    dataRiferimento.add(Calendar.YEAR, 1);
	    logger.debug("dataRiferimento: {}, dataPartenza: {}", dataRiferimento, dataPartenza);
	    return dataRiferimento.getTime();
	} else {
	    int gg = Integer.parseInt(rif[0]);
	    String data = periodi[0] + "/" + dataPartenza.get(Calendar.MONTH) + "/" + dataPartenza.get(Calendar.YEAR);
	    if (!this.isValid(data)) {
		throw new IllegalArgumentException(
			"Errore in fase di configurazione. La data di scadenza dovrebbe essere " + data + " e non è una data valida!");
	    }
	    dataRiferimento.set(dataPartenza.get(Calendar.YEAR), dataPartenza.get(Calendar.MONTH), gg);
	    if (Utilities.compareDates(dataRiferimento.getTime(), dataPartenza.getTime()) >= 0) {
		return dataRiferimento.getTime();
	    }
	    dataRiferimento.add(Calendar.MONTH, 1);
	    return dataRiferimento.getTime();
	}
    }

    private boolean isValid(String dateStr) {

	DateFormat sdf = new SimpleDateFormat(this.dateFormat);
	sdf.setLenient(false);
	try {
	    sdf.parse(dateStr);
	} catch (ParseException e) {
	    return false;
	}
	return true;
    }
}
