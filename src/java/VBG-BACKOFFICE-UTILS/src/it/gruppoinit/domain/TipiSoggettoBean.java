package it.gruppoinit.domain;

import java.io.Serializable;

public class TipiSoggettoBean implements Serializable {

    public TipiSoggettoBean() {

	super();
    }

    public TipiSoggettoBean(Integer codice, String tiposoggetto, Integer flagQualita, String utilizzo, Integer richiediAnagrafeColl,
	    Integer flgSpecDesc, Integer flgLegaleR, Integer ordine, Integer atribQualifica, Integer flgMostraDettIstanza) {

	this();
	this.codice = codice;
	this.tiposoggetto = tiposoggetto;
	this.flagQualita = flagQualita;
	this.utilizzo = utilizzo;
	this.richiediAnagrafeColl = richiediAnagrafeColl;
	this.flgSpecDesc = flgSpecDesc;
	this.flgLegaleR = flgLegaleR;
	this.ordine = ordine;
	this.atribQualifica = atribQualifica;
	this.flgMostraDettIstanza = flgMostraDettIstanza;
    }

    /**
     * 
     */
    private static final long serialVersionUID = -1605630027519284361L;
    //    SELECT CODICETIPOSOGGETTO,TIPOSOGGETTO,FLAGQUALITA,UTILIZZO,RICHIEDIANAGRAFECOLL," //
    //	+ "FLG_SPECIFICADESCRIZIONE,FLG_LEGALERAP,ORDINE,ATRIB_QUALIFICASOGGETTO,FLAG_MOSTRA_DETT_ISTANZA
    private Integer codice;
    private String tiposoggetto;
    private Integer flagQualita;
    private String utilizzo;
    private Integer richiediAnagrafeColl;
    private Integer flgSpecDesc;
    private Integer flgLegaleR;
    private Integer ordine;
    private Integer atribQualifica;
    private Integer flgMostraDettIstanza;

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public String getTiposoggetto() {

	return tiposoggetto;
    }

    public void setTiposoggetto(String tiposoggetto) {

	this.tiposoggetto = tiposoggetto;
    }

    public Integer getFlagQualita() {

	return flagQualita;
    }

    public void setFlagQualita(Integer flagQualita) {

	this.flagQualita = flagQualita;
    }

    public String getUtilizzo() {

	return utilizzo;
    }

    public void setUtilizzo(String utilizzo) {

	this.utilizzo = utilizzo;
    }

    public Integer getRichiediAnagrafeColl() {

	return richiediAnagrafeColl;
    }

    public void setRichiediAnagrafeColl(Integer richiediAnagrafeColl) {

	this.richiediAnagrafeColl = richiediAnagrafeColl;
    }

    public Integer getFlgSpecDesc() {

	return flgSpecDesc;
    }

    public void setFlgSpecDesc(Integer flgSpecDesc) {

	this.flgSpecDesc = flgSpecDesc;
    }

    public Integer getFlgLegaleR() {

	return flgLegaleR;
    }

    public void setFlgLegaleR(Integer flgLegaleR) {

	this.flgLegaleR = flgLegaleR;
    }

    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    public Integer getAtribQualifica() {

	return atribQualifica;
    }

    public void setAtribQualifica(Integer atribQualifica) {

	this.atribQualifica = atribQualifica;
    }

    public Integer getFlgMostraDettIstanza() {

	return flgMostraDettIstanza;
    }

    public void setFlgMostraDettIstanza(Integer flgMostraDettIstanza) {

	this.flgMostraDettIstanza = flgMostraDettIstanza;
    }
}
