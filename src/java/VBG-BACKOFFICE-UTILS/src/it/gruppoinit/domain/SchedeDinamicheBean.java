package it.gruppoinit.domain;

import java.io.Serializable;
import java.util.List;

public class SchedeDinamicheBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8188176451902308948L;
    private Integer codice;
    private String software;
    private String descrizione;
    private String d2bcid;
    private String scriptCodce;
    private Integer modellomultiplo;
    private Integer flgStoricizza;
    private Integer flagReadOnlyWeb;
    private Integer modelloFrontoffice;
    private String codiceScheda;
    private List<SchedeDinamicheDettaglioTestiBean> testis;
    private List<SchedeDinamicheScriptBean> scripts;
    private List<SchedeDinamicheDettaglioBean> dettaglios;

    public SchedeDinamicheBean() {

	super();
    }

    public SchedeDinamicheBean(Integer codice, String software, String descrizione, String d2bcid, String scriptCodce, Integer modellomultiplo,
	    Integer flgStoricizza, Integer flagReadOnlyWeb, Integer modelloFrontoffice, String codiceScheda) {

	this();
	this.codice = codice;
	this.software = software;
	this.descrizione = descrizione;
	this.d2bcid = d2bcid;
	this.scriptCodce = scriptCodce;
	this.modellomultiplo = modellomultiplo;
	this.flgStoricizza = flgStoricizza;
	this.flagReadOnlyWeb = flagReadOnlyWeb;
	this.modelloFrontoffice = modelloFrontoffice;
	this.codiceScheda = codiceScheda;
    }

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getD2bcid() {

	return d2bcid;
    }

    public void setD2bcid(String d2bcid) {

	this.d2bcid = d2bcid;
    }

    public String getScriptCodce() {

	return scriptCodce;
    }

    public void setScriptCodce(String scriptCodce) {

	this.scriptCodce = scriptCodce;
    }

    public Integer getModellomultiplo() {

	return modellomultiplo;
    }

    public void setModellomultiplo(Integer modellomultiplo) {

	this.modellomultiplo = modellomultiplo;
    }

    public Integer getFlgStoricizza() {

	return flgStoricizza;
    }

    public void setFlgStoricizza(Integer flgStoricizza) {

	this.flgStoricizza = flgStoricizza;
    }

    public Integer getFlagReadOnlyWeb() {

	return flagReadOnlyWeb;
    }

    public void setFlagReadOnlyWeb(Integer flagReadOnlyWeb) {

	this.flagReadOnlyWeb = flagReadOnlyWeb;
    }

    public Integer getModelloFrontoffice() {

	return modelloFrontoffice;
    }

    public void setModelloFrontoffice(Integer modelloFrontoffice) {

	this.modelloFrontoffice = modelloFrontoffice;
    }

    public String getCodiceScheda() {

	return codiceScheda;
    }

    public void setCodiceScheda(String codiceScheda) {

	this.codiceScheda = codiceScheda;
    }

    public List<SchedeDinamicheScriptBean> getScripts() {

	return scripts;
    }

    public void setScripts(List<SchedeDinamicheScriptBean> scripts) {

	this.scripts = scripts;
    }

    public List<SchedeDinamicheDettaglioBean> getDettaglios() {

	return dettaglios;
    }

    public void setDettaglios(List<SchedeDinamicheDettaglioBean> dettaglios) {

	this.dettaglios = dettaglios;
    }

    public List<SchedeDinamicheDettaglioTestiBean> getTestis() {

	return testis;
    }

    public void setTestis(List<SchedeDinamicheDettaglioTestiBean> testis) {

	this.testis = testis;
    }
}
