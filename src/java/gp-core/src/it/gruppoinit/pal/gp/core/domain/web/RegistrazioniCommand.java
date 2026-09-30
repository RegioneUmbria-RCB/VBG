package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Registrazioni;

public class RegistrazioniCommand extends BaseCommand {

    public RegistrazioniCommand() {

	this.entity = new Registrazioni();
	this.regRiduzioneAccertamento = new Registrazioni();
	this.transazioniHelper = new TransazioniHelper();
    }

    private Registrazioni entity;
    private Registrazioni regRiduzioneAccertamento;
    private String regImportiSelezionati;
    private TransazioniHelper transazioniHelper;

    public TransazioniHelper getTransazioniHelper() {

	return transazioniHelper;
    }

    public void setTransazioniHelper(TransazioniHelper transazioniHelper) {

	this.transazioniHelper = transazioniHelper;
    }

    public void setEntity(Registrazioni entity) {

	this.entity = entity;
    }

    public Registrazioni getEntity() {

	return entity;
    }

    public void setRegRiduzioneAccertamento(Registrazioni regRiduzioneAccertamento) {

	this.regRiduzioneAccertamento = regRiduzioneAccertamento;
    }

    public Registrazioni getRegRiduzioneAccertamento() {

	return regRiduzioneAccertamento;
    }

    public void setRegImportiSelezionati(String regImportiSelezionati) {

	this.regImportiSelezionati = regImportiSelezionati;
    }

    public String getRegImportiSelezionati() {

	return regImportiSelezionati;
    }
}
