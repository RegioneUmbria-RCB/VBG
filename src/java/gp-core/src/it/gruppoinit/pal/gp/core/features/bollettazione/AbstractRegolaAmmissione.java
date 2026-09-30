package it.gruppoinit.pal.gp.core.features.bollettazione;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.utils.DateUtils;

public abstract class AbstractRegolaAmmissione implements IRegolaAmmissione {

    private String messaggio;
    private final String separatore = ", ";

    @Override
    public String getErroriValidazione() {

	return messaggio;
    }

    @Override
    public abstract boolean valida();

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }

    public List<Date> getGiorniBollettazione(Date dataInizio, Date dataFine) {

	return DateUtils.getDaysBetweenDates(dataInizio, dataFine);
    }

    public String getSeparatore() {

	return separatore;
    }
}
