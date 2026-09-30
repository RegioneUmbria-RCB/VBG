package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import it.gruppoinit.pal.gp.core.dao.helper.PeriodiEnum;

public class DettaglioPeriodicitaHelper {

    private Integer anno;
    private List<DettaglioPeriodicitaElementi> elementi;
    private PeriodiEnum periodicita;

    public PeriodiEnum getPeriodicita() {

	return periodicita;
    }

    private CalcoloIntervalloStrategy strategy;

    public DettaglioPeriodicitaHelper(Integer anno, PeriodiEnum periodicita) {

	super();
	if (anno == null || periodicita == null) {
	    throw new IllegalArgumentException("Parametri non validi");
	}
	this.anno = anno;
	this.periodicita = periodicita;
	CalcoloIntervalloFactory factory = new CalcoloIntervalloFactory();
	strategy = factory.get(periodicita);
	popolaListaElementi();
    }

    private void popolaListaElementi() {

	GregorianCalendar calendar = new GregorianCalendar();
	calendar.set(Calendar.YEAR, this.anno);
	calendar.set(Calendar.DAY_OF_MONTH, 15);
	switch (this.periodicita) {
	case MENSILE:
	    SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.ITALIAN);
	    for (int i = 0; i < 12; i++) {
		calendar.set(Calendar.MONTH, i);
		IntervalloDate interDate = strategy.getIntervallo(calendar.getTime());
		String mese = sdf.format(calendar.getTime());
		this.getElementi().add(new DettaglioPeriodicitaElementi(getChiaveFromIntervallo(interDate), mese));
	    }
	    break;
	case BIMESTRALE:
	    int j = 1;
	    for (int i = 0; i < 12; i += 2) {
		calendar.set(Calendar.MONTH, i);
		IntervalloDate interDate = strategy.getIntervallo(calendar.getTime());
		String descrizione = (j) + " Bimestre";
		j++;
		this.getElementi().add(new DettaglioPeriodicitaElementi(getChiaveFromIntervallo(interDate), descrizione));
	    }
	    break;
	case TRIMESTRALE:
	    j = 1;
	    for (int i = 0; i < 12; i += 3) {
		calendar.set(Calendar.MONTH, i);
		IntervalloDate interDate = strategy.getIntervallo(calendar.getTime());
		String descrizione = (j) + " Trimestre";
		j++;
		this.getElementi().add(new DettaglioPeriodicitaElementi(getChiaveFromIntervallo(interDate), descrizione));
	    }
	    break;
	case QUADRIMESTRALE:
	    j = 1;
	    for (int i = 0; i < 12; i += 4) {
		calendar.set(Calendar.MONTH, i);
		IntervalloDate interDate = strategy.getIntervallo(calendar.getTime());
		String descrizione = (j) + " Quadrimestre";
		j++;
		this.getElementi().add(new DettaglioPeriodicitaElementi(getChiaveFromIntervallo(interDate), descrizione));
	    }
	    break;
	case SEMESTRALE:
	    j = 1;
	    for (int i = 0; i < 12; i += 6) {
		calendar.set(Calendar.MONTH, i);
		IntervalloDate interDate = strategy.getIntervallo(calendar.getTime());
		String descrizione = (j) + " Semestrale";
		j++;
		this.getElementi().add(new DettaglioPeriodicitaElementi(getChiaveFromIntervallo(interDate), descrizione));
	    }
	    break;
	case ANNUALE:
	    calendar.set(Calendar.MONTH, 0);
	    IntervalloDate interDate = strategy.getIntervallo(calendar.getTime());
	    String descrizione = "Anno " + this.anno;
	    this.getElementi().add(new DettaglioPeriodicitaElementi(getChiaveFromIntervallo(interDate), descrizione));
	    break;
	default:
	    throw new IllegalArgumentException("Periodicità non Implementato");
	}
    }

    private String getChiaveFromIntervallo(IntervalloDate intervalloDate) {

	SimpleDateFormat sdf = new SimpleDateFormat("MMdd", Locale.ITALIAN);
	Date dataInizio = intervalloDate.getDataInizio();
	Date dataFine = intervalloDate.getDataFine();
	return sdf.format(dataInizio) + "-" + sdf.format(dataFine);
    }

    public List<DettaglioPeriodicitaElementi> getElementi() {

	if (elementi == null) {
	    elementi = new ArrayList<DettaglioPeriodicitaElementi>();
	}
	return elementi;
    }

    public Integer getAnno() {

	return anno;
    }
}
