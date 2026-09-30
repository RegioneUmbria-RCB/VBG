package it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model;

import java.util.ArrayList;
import java.util.List;

public class AppioInterventiPerSofwtareBean {

    private String codice;
    private String descrizione;
    private List<AppioTipimovimentoInterventoBean> interventi;

    public AppioInterventiPerSofwtareBean() {

	super();
    }

    public AppioInterventiPerSofwtareBean(String codice, String descrizione, List<AppioTipimovimentoInterventoBean> interventi) {

	this();
	this.codice = codice;
	this.descrizione = descrizione;
	this.interventi = interventi;
    }

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public List<AppioTipimovimentoInterventoBean> getInterventi() {

	if (this.interventi == null) {
	    this.interventi = new ArrayList<AppioTipimovimentoInterventoBean>();
	}
	return interventi;
    }

    public void setInterventi(List<AppioTipimovimentoInterventoBean> interventi) {

	this.interventi = interventi;
    }
}
