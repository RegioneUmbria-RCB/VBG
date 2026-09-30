package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;

import java.util.Date;

public class FiltriRicercafoDomande {

    private Date dataInvioDa;
    private Date dataInvioA;
    private String identificativoDomanda;
    private Anagrafe anagrafe;
    private String titolarePratica;
    private Boolean presentata;
    private Boolean escludiDomandeComunica;

    public Anagrafe getAnagrafe() {

	return anagrafe;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    public Boolean getPresentata() {

	return presentata;
    }

    public void setPresentata(Boolean presentata) {

	this.presentata = presentata;
    }

    public Date getDataInvioDa() {

	return dataInvioDa;
    }

    public void setDataInvioDa(Date dataInvioDa) {

	this.dataInvioDa = dataInvioDa;
    }

    public Date getDataInvioA() {

	return dataInvioA;
    }

    public void setDataInvioA(Date dataInvioA) {

	this.dataInvioA = dataInvioA;
    }

    public String getIdentificativoDomanda() {

	return identificativoDomanda;
    }

    public void setIdentificativoDomanda(String identificativoDomanda) {

	this.identificativoDomanda = identificativoDomanda;
    }

    public String getTitolarePratica() {

	return titolarePratica;
    }

    public void setTitolarePratica(String titolarePratica) {

	this.titolarePratica = titolarePratica;
    }

    public Boolean getEscludiDomandeComunica() {

	return escludiDomandeComunica;
    }

    public void setEscludiDomandeComunica(Boolean escludiDomandeComunica) {

	this.escludiDomandeComunica = escludiDomandeComunica;
    }
}
