package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.dao.helper.PeriodiEnum;
import it.gruppoinit.pal.gp.core.dao.helper.TitolaritaPagamentiEnum;

public class RichiestaCalcoloBollettazioneMercato implements RichiestaCalcoloBollettazione {

    private PeriodiEnum tipologiaPeriodo;
    private Integer codiceResponsabile;
    private String descrizione;
    private IntervalloDate intervalloDate;
    private List<Integer> filtriMercati;
    private List<Integer> filtriConti;
    private TitolaritaPagamentiEnum titolarita;

    public RichiestaCalcoloBollettazioneMercato(PeriodiEnum tipologiaPeriodo, TitolaritaPagamentiEnum titolarita, Integer codiceResponsabile,
	    String descrizione, IntervalloDate intervalloDate, List<Integer> filtriConti) {

	super();
	this.tipologiaPeriodo = tipologiaPeriodo;
	this.codiceResponsabile = codiceResponsabile;
	this.descrizione = descrizione;
	this.intervalloDate = intervalloDate;
	this.titolarita = titolarita;
	this.filtriConti = filtriConti;
    }

    public void setTipologiaPeriodo(PeriodiEnum tipologiaPeriodo) {

	this.tipologiaPeriodo = tipologiaPeriodo;
    }

    public PeriodiEnum getTipologiaPeriodo() {

	return this.tipologiaPeriodo;
    }

    public void setCodiceResponsabile(Integer codiceResponsabile) {

	this.codiceResponsabile = codiceResponsabile;
    }

    public Integer getCodiceResponsabile() {

	return this.codiceResponsabile;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getDescrizione() {

	return this.descrizione;
    }

    public IntervalloDate getIntervalloDate() {

	return intervalloDate;
    }

    public void setIntervalloDate(IntervalloDate intervalloDate) {

	this.intervalloDate = intervalloDate;
    }

    public List<Integer> getFiltriMercati() {

	if (this.filtriMercati == null) {
	    this.filtriMercati = new ArrayList<Integer>();
	}
	return filtriMercati;
    }

    public TitolaritaPagamentiEnum getTitolarita() {

	return titolarita;
    }

    public void setTitolarita(TitolaritaPagamentiEnum titolarita) {

	this.titolarita = titolarita;
    }

    public List<Integer> getFiltriConti() {

	if (this.filtriConti == null) {
	    this.filtriConti = new ArrayList<Integer>();
	}
	return filtriConti;
    }
}
