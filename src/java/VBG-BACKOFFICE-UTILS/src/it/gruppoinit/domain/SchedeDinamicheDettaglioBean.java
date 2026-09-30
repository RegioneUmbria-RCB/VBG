package it.gruppoinit.domain;

import java.io.Serializable;

public class SchedeDinamicheDettaglioBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6814982258102447895L;
    private Integer codice;
    private Integer fkd2cid;
    private Integer fkd2mdtid;
    private Integer posverticale;
    private Integer posorizzontale;
    private Integer flagMultiplo;
    private Integer fkRegolaAttivo;
    private Integer flagObbligatorio;
    private Integer flagSpezza;

    public SchedeDinamicheDettaglioBean() {

	super();
    }

    public SchedeDinamicheDettaglioBean(Integer codice, Integer fkd2cid, Integer fkd2mdtid, Integer posverticale, Integer posorizzontale,
	    Integer flagMultiplo, Integer fkRegolaAttivo, Integer flagObbligatorio, Integer flagSpezza) {

	this();
	this.codice = codice;
	this.fkd2cid = fkd2cid;
	this.fkd2mdtid = fkd2mdtid;
	this.posverticale = posverticale;
	this.posorizzontale = posorizzontale;
	this.flagMultiplo = flagMultiplo;
	this.fkRegolaAttivo = fkRegolaAttivo;
	this.flagObbligatorio = flagObbligatorio;
	this.flagSpezza = flagSpezza;
    }

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public Integer getFkd2cid() {

	return fkd2cid;
    }

    public void setFkd2cid(Integer fkd2cid) {

	this.fkd2cid = fkd2cid;
    }

    public Integer getFkd2mdtid() {

	return fkd2mdtid;
    }

    public void setFkd2mdtid(Integer fkd2mdtid) {

	this.fkd2mdtid = fkd2mdtid;
    }

    public Integer getPosverticale() {

	return posverticale;
    }

    public void setPosverticale(Integer posverticale) {

	this.posverticale = posverticale;
    }

    public Integer getPosorizzontale() {

	return posorizzontale;
    }

    public void setPosorizzontale(Integer posorizzontale) {

	this.posorizzontale = posorizzontale;
    }

    public Integer getFlagMultiplo() {

	return flagMultiplo;
    }

    public void setFlagMultiplo(Integer flagMultiplo) {

	this.flagMultiplo = flagMultiplo;
    }

    public Integer getFkRegolaAttivo() {

	return fkRegolaAttivo;
    }

    public void setFkRegolaAttivo(Integer fkRegolaAttivo) {

	this.fkRegolaAttivo = fkRegolaAttivo;
    }

    public Integer getFlagObbligatorio() {

	return flagObbligatorio;
    }

    public void setFlagObbligatorio(Integer flagObbligatorio) {

	this.flagObbligatorio = flagObbligatorio;
    }

    public Integer getFlagSpezza() {

	return flagSpezza;
    }

    public void setFlagSpezza(Integer flagSpezza) {

	this.flagSpezza = flagSpezza;
    }
}
