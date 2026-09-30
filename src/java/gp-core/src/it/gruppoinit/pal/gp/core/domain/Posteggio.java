package it.gruppoinit.pal.gp.core.domain;

import java.util.List;

public class Posteggio {

    private MercatiD posteggio;
    private MercatiUso mercatiUso;
    private List<SituazioneContabile> situazioneContabileList;
    // aggiunti per la funzionalità pagamento utenze posteggi
    private Anagrafe anagrafe;
    private List<RegistrazioniImporti> registrazioniimportiposteggioList;
    // Flag per la visuallizazione (funzionalità inserisci registrazioni)
    private Boolean visualizza;

    public MercatiD getPosteggio() {

	return posteggio;
    }

    public void setPosteggio(MercatiD posteggio) {

	this.posteggio = posteggio;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public MercatiUso getMercatiUso() {

	return mercatiUso;
    }

    public List<SituazioneContabile> getSituazioneContabileList() {

	return situazioneContabileList;
    }

    public void setSituazioneContabileList(List<SituazioneContabile> situazioneContabileList) {

	this.situazioneContabileList = situazioneContabileList;
    }

    public Anagrafe getAnagrafe() {

	return anagrafe;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    public void setRegistrazioniimportiposteggioList(List<RegistrazioniImporti> registrazioniimportiposteggioList) {

	this.registrazioniimportiposteggioList = registrazioniimportiposteggioList;
    }

    public List<RegistrazioniImporti> getRegistrazioniimportiposteggioList() {

	return registrazioniimportiposteggioList;
    }

    public void setVisualizza(Boolean visualizza) {

	this.visualizza = visualizza;
    }

    public Boolean getVisualizza() {

	return visualizza;
    }
}
