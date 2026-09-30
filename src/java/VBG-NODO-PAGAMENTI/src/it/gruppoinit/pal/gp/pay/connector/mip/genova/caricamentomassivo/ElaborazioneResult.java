package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo;

import java.math.BigDecimal;

import it.gruppoinit.pal.gp.pay.tracciati.TracciatoRecordSet;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordDebito;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordRata;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordRipartizione;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;

public class ElaborazioneResult {

    private String errore;
    private Boolean erroreTemporaneo;
    private int numeroRate;
    private BigDecimal totaleDebito;
    private TracciatoRecordSet<TracciatoRecordDebito> debiti;
    private TracciatoRecordSet<TracciatoRecordRipartizione> ripartizioni;
    private TracciatoRecordSet<TracciatoRecordRata> rate;

    public ElaborazioneResult() {

	this.numeroRate = 0;
	this.totaleDebito = BigDecimal.ZERO;
	this.debiti = new TracciatoRecordSet<>();
	this.ripartizioni = new TracciatoRecordSet<>();
	this.rate = new TracciatoRecordSet<>();
    }

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }

    public Boolean getErroreTemporaneo() {

	return erroreTemporaneo;
    }

    public void setErroreTemporaneo(Boolean erroreTemporaneo) {

	this.erroreTemporaneo = erroreTemporaneo;
    }

    public TracciatoRecordSet<TracciatoRecordDebito> getDebiti() {

	return debiti;
    }

    public void setDebiti(TracciatoRecordSet<TracciatoRecordDebito> debiti) {

	this.debiti = debiti;
    }

    public TracciatoRecordSet<TracciatoRecordRipartizione> getRipartizioni() {

	return ripartizioni;
    }

    public void setRipartizioni(TracciatoRecordSet<TracciatoRecordRipartizione> ripartizioni) {

	this.ripartizioni = ripartizioni;
    }

    public TracciatoRecordSet<TracciatoRecordRata> getRate() {

	return rate;
    }

    public void setRate(TracciatoRecordSet<TracciatoRecordRata> rate) {

	this.rate = rate;
    }

    public int getNumeroRate() {

	return numeroRate;
    }

    public void setNumeroRate(int numeroRate) {

	this.numeroRate = numeroRate;
    }

    public BigDecimal getTotaleDebito() {

	return totaleDebito;
    }

    public void addTotaleDebito(BigDecimal importoDaAggiungere) {

	this.totaleDebito = this.totaleDebito.add(importoDaAggiungere);
    }

    public static ElaborazioneResult fromEsitoOperazionePosizioneDebitoriaType(EsitoOperazionePosizioneDebitoriaType exceptionInfo) {

	ElaborazioneResult result = new ElaborazioneResult();
	result.setErrore(exceptionInfo.getMessaggio());
	result.setErroreTemporaneo(exceptionInfo.isErroreTemporaneo());
	return result;
    }
}
