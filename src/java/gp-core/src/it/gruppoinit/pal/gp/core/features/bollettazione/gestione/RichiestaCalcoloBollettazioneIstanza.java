package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.dao.helper.PeriodiEnum;

public class RichiestaCalcoloBollettazioneIstanza implements RichiestaCalcoloBollettazione {

    private PeriodiEnum tipologiaPeriodo;
    private Integer codiceResponsabile;
    private String descrizione;
    private IntervalloDate intervalloDate;
    private List<String> filtriScCodice;
    private List<Integer> filtriCodiceEndo;
    private List<String> filtriCodiceComune;
    private List<Integer> filtriCausaleOnere;

    public RichiestaCalcoloBollettazioneIstanza(PeriodiEnum tipologiaPeriodo, Integer codiceResponsabile, String descrizione,
	    IntervalloDate intervalloDate) {

	this.tipologiaPeriodo = tipologiaPeriodo;
	this.codiceResponsabile = codiceResponsabile;
	this.descrizione = descrizione;
	this.intervalloDate = intervalloDate;
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

    public List<String> getFiltriScCodice() {

	if (this.filtriScCodice == null) {
	    this.filtriScCodice = new ArrayList<String>();
	}
	return this.filtriScCodice;
    }

    public List<Integer> getFiltriCodiceEndo() {

	if (this.filtriCodiceEndo == null) {
	    this.filtriCodiceEndo = new ArrayList<Integer>();
	}
	return filtriCodiceEndo;
    }

    public List<String> getFiltriCodiceComune() {

	if (this.filtriCodiceComune == null) {
	    this.filtriCodiceComune = new ArrayList<String>();
	}
	return filtriCodiceComune;
    }

    public List<Integer> getFiltriCausaleOnere() {

	if (this.filtriCausaleOnere == null) {
	    this.filtriCausaleOnere = new ArrayList<Integer>();
	}
	return filtriCausaleOnere;
    }

    public IntervalloDate getIntervalloDate() {

	return intervalloDate;
    }

    public void setIntervalloDate(IntervalloDate intervalloDate) {

	this.intervalloDate = intervalloDate;
    }
}
