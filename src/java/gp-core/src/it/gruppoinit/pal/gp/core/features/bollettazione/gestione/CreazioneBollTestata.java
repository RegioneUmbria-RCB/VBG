package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.Set;

public class CreazioneBollTestata {

    private String descrizione;
    private Integer bollCfgTipoId;
    private Set<String> comuni;
    private Set<String> interventi;
    private Set<Integer> endoprocedimenti;
    private IntervalloDate intervalloDate;

    public IntervalloDate getIntervalloDate() {

	return intervalloDate;
    }

    public void setIntervalloDate(IntervalloDate intervalloDate) {

	this.intervalloDate = intervalloDate;
    }

    public CreazioneBollTestata() {

	this.intervalloDate = new IntervalloDate(null, null);
    }

    public Set<String> getComuni() {

	return comuni;
    }

    public void setComuni(Set<String> comuni) {

	this.comuni = comuni;
    }

    public Set<String> getInterventi() {

	return interventi;
    }

    public void setInterventi(Set<String> interventi) {

	this.interventi = interventi;
    }

    public Set<Integer> getEndoprocedimenti() {

	return endoprocedimenti;
    }

    public void setEndoprocedimenti(Set<Integer> endoprocedimenti) {

	this.endoprocedimenti = endoprocedimenti;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public Integer getBollCfgTipoId() {

	return bollCfgTipoId;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public void setBollCfgTipoId(Integer bollCfgTipoId) {

	this.bollCfgTipoId = bollCfgTipoId;
    }
}
