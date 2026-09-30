package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.ComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniTMercato;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

public class ComunicazioniTMercatoCommand {

    private ComunicazioniTMercato entity;
    private ComunicazioniT comunicazioniT;
    private CodiceDescrizioneBean firmatari;
    private Boolean flagbloccaconfigurazione;

    public ComunicazioniTMercatoCommand() {

	this.entity = new ComunicazioniTMercato();
	this.firmatari = new CodiceDescrizioneBean();
	this.comunicazioniT = new ComunicazioniT();
	this.flagbloccaconfigurazione = Boolean.FALSE;
    }

    public Boolean getFlagbloccaconfigurazione() {

	return flagbloccaconfigurazione;
    }

    public void setFlagbloccaconfigurazione(Boolean flagbloccaconfigurazione) {

	this.flagbloccaconfigurazione = flagbloccaconfigurazione;
    }

    public ComunicazioniTMercato getEntity() {

	return entity;
    }

    public void setEntity(ComunicazioniTMercato entity) {

	this.entity = entity;
    }

    public ComunicazioniT getComunicazioniT() {

	return comunicazioniT;
    }

    public void setComunicazioniT(ComunicazioniT comunicazioniT) {

	this.comunicazioniT = comunicazioniT;
    }

    public CodiceDescrizioneBean getFirmatari() {

	return firmatari;
    }

    public void setFirmatari(CodiceDescrizioneBean firmatari) {

	this.firmatari = firmatari;
    }
}
