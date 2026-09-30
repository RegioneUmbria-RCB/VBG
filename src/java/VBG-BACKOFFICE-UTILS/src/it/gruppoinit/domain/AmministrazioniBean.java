package it.gruppoinit.domain;

import java.io.Serializable;

public class AmministrazioniBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5123046601212336433L;
    private Integer codiceAmministrazione;
    private String amministrazione;
    private String ufficio;
    private String referente;
    private String indirizzo;
    private String citta;
    private String cap;
    private String provincia;
    private String telefono1;
    private String telefono2;
    private String fax;
    private String email;
    private String web;
    private Integer flagSilenzio;
    private String codiceAncitel;
    private String progressivoExp;
    private String piva;
    private String stcIdEnte;
    private String stcIdSportello;
    private String pec;
    private Integer flagDisabilitato;
    private String stcIdNodo;

    public AmministrazioniBean() {

	super();
    }

    public Integer getCodiceAmministrazione() {

	return codiceAmministrazione;
    }

    public void setCodiceAmministrazione(Integer codiceAmministrazione) {

	this.codiceAmministrazione = codiceAmministrazione;
    }

    public String getAmministrazione() {

	return amministrazione;
    }

    public void setAmministrazione(String amministrazione) {

	this.amministrazione = amministrazione;
    }

    public String getUfficio() {

	return ufficio;
    }

    public void setUfficio(String ufficio) {

	this.ufficio = ufficio;
    }

    public String getReferente() {

	return referente;
    }

    public void setReferente(String referente) {

	this.referente = referente;
    }

    public String getIndirizzo() {

	return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
    }

    public String getCitta() {

	return citta;
    }

    public void setCitta(String citta) {

	this.citta = citta;
    }

    public String getCap() {

	return cap;
    }

    public void setCap(String cap) {

	this.cap = cap;
    }

    public String getProvincia() {

	return provincia;
    }

    public void setProvincia(String provincia) {

	this.provincia = provincia;
    }

    public String getTelefono1() {

	return telefono1;
    }

    public void setTelefono1(String telefono1) {

	this.telefono1 = telefono1;
    }

    public String getTelefono2() {

	return telefono2;
    }

    public void setTelefono2(String telefono2) {

	this.telefono2 = telefono2;
    }

    public String getFax() {

	return fax;
    }

    public void setFax(String fax) {

	this.fax = fax;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public String getWeb() {

	return web;
    }

    public void setWeb(String web) {

	this.web = web;
    }

    public Integer getFlagSilenzio() {

	return flagSilenzio;
    }

    public void setFlagSilenzio(Integer flagSilenzio) {

	this.flagSilenzio = flagSilenzio;
    }

    public String getCodiceAncitel() {

	return codiceAncitel;
    }

    public void setCodiceAncitel(String codiceAncitel) {

	this.codiceAncitel = codiceAncitel;
    }

    public String getProgressivoExp() {

	return progressivoExp;
    }

    public void setProgressivoExp(String progressivoExp) {

	this.progressivoExp = progressivoExp;
    }

    public String getPiva() {

	return piva;
    }

    public void setPiva(String piva) {

	this.piva = piva;
    }

    public String getStcIdEnte() {

	return stcIdEnte;
    }

    public void setStcIdEnte(String stcIdEnte) {

	this.stcIdEnte = stcIdEnte;
    }

    public String getStcIdSportello() {

	return stcIdSportello;
    }

    public void setStcIdSportello(String stcIdSportello) {

	this.stcIdSportello = stcIdSportello;
    }

    public String getPec() {

	return pec;
    }

    public void setPec(String pec) {

	this.pec = pec;
    }

    public Integer getFlagDisabilitato() {

	return flagDisabilitato;
    }

    public void setFlagDisabilitato(Integer flagDisabilitato) {

	this.flagDisabilitato = flagDisabilitato;
    }

    public String getStcIdNodo() {

	return stcIdNodo;
    }

    public void setStcIdNodo(String stcIdNodo) {

	this.stcIdNodo = stcIdNodo;
    }
}
