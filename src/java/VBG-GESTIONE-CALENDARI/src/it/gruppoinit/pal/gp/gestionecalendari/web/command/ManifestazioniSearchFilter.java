package it.gruppoinit.pal.gp.gestionecalendari.web.command;

import java.util.Date;

public class ManifestazioniSearchFilter {

    private String tipoManifestazione;
    private String denominazione;
    private String tipologia;
    private Date dal;
    private Date al;
    private String luogoSvolgimento;
    private String organizzatore;
    private String codicecomune;
    private String comune;
    private Date dataInserimento;

    public ManifestazioniSearchFilter() {

    }

    public ManifestazioniSearchFilter(String tipoManifestazione) {

	this.tipoManifestazione = tipoManifestazione;
    }

    public String getDenominazione() {

	return denominazione;
    }

    public void setDenominazione(String denominazione) {

	this.denominazione = denominazione;
    }

    public String getTipologia() {

	return tipologia;
    }

    public void setTipologia(String tipologia) {

	this.tipologia = tipologia;
    }

    public Date getDal() {

	return dal;
    }

    public void setDal(Date dal) {

	this.dal = dal;
    }

    public Date getAl() {

	return al;
    }

    public void setAl(Date al) {

	this.al = al;
    }

    public String getLuogoSvolgimento() {

	return luogoSvolgimento;
    }

    public void setLuogoSvolgimento(String luogoSvolgimento) {

	this.luogoSvolgimento = luogoSvolgimento;
    }

    public String getOrganizzatore() {

	return organizzatore;
    }

    public void setOrganizzatore(String organizzatore) {

	this.organizzatore = organizzatore;
    }

    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getTipoManifestazione() {

	return tipoManifestazione;
    }

    public void setTipoManifestazione(String tipoManifestazione) {

	this.tipoManifestazione = tipoManifestazione;
    }

    public Date getDataInserimento() {

	return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {

	this.dataInserimento = dataInserimento;
    }
}
