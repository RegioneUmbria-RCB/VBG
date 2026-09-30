package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.Date;

public class IntervalloDate {

    private Date dataInizio;
    private Date dataFine;

    public IntervalloDate(Date dataInizio, Date dataFine) {

	this.dataInizio = dataInizio;
	this.dataFine = dataFine;
    }

    public void setDataInizio(Date dataInizio) {

	this.dataInizio = dataInizio;
    }

    public void setDataFine(Date dataFine) {

	this.dataFine = dataFine;
    }

    public Date getDataInizio() {

	return this.dataInizio;
    }

    public Date getDataFine() {

	return this.dataFine;
    }

    public static IntervalloDate fromIntervalloDate(IntervalloDate intervalloDate) {

	return new IntervalloDate(intervalloDate.getDataInizio(), intervalloDate.getDataFine());
    }
}
