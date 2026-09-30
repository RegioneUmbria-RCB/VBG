package it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio;

public class SorteggioDettaglioPerInserimentoDTO {

    private Integer codiceIstanza;
    private Boolean sorteggiata;
    private Boolean flagInterventoObbligatorio;

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public Boolean getSorteggiata() {

	return sorteggiata;
    }

    public Boolean getFlagInterventoObbligatorio() {

	return flagInterventoObbligatorio;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public void setSorteggiata(Boolean sorteggiata) {

	this.sorteggiata = sorteggiata;
    }

    public void setFlagInterventoObbligatorio(Boolean flagInterventoObbligatorio) {

	this.flagInterventoObbligatorio = flagInterventoObbligatorio;
    }
}
