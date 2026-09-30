package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.VwEntilocali;

public class AutorizzazioniHelper {

    private Autorizzazioni autorizzazione;
    private AutorizzazioniConcessioni concessione;
    private AutorizzazioniHelper autorizzazioneCollegataHelper;
    private String autoriznumeroSubentro;
    private Date autorizdataSubentro;
    private Date dataRilascioSubentro;
    private Date autorizdatascadenzaSubentro;
    private VwEntilocali autorizcomuneSubentro;
    private Tipologiaregistri autorizregistroSubentro;
    private Date dataCessazione;
    private boolean daSubentrare;
    private boolean registroAutomatico;
    private boolean flagMantieniNumero;
    private boolean flagAffitto;

    public AutorizzazioniHelper() {

	this.autorizcomuneSubentro = new VwEntilocali();
	this.autorizregistroSubentro = new Tipologiaregistri();
	this.autorizzazione = new Autorizzazioni();
	this.concessione = new AutorizzazioniConcessioni();
	this.flagMantieniNumero = false;
	this.flagAffitto = false;
    }

    public Autorizzazioni getAutorizzazione() {

	return autorizzazione;
    }

    public void setAutorizzazione(Autorizzazioni autorizzazione) {

	this.autorizzazione = autorizzazione;
    }

    public String getAutoriznumeroSubentro() {

	return autoriznumeroSubentro;
    }

    public void setAutoriznumeroSubentro(String autoriznumeroSubentro) {

	this.autoriznumeroSubentro = autoriznumeroSubentro;
    }

    public Date getAutorizdataSubentro() {

	return autorizdataSubentro;
    }

    public void setAutorizdataSubentro(Date autorizdataSubentro) {

	this.autorizdataSubentro = autorizdataSubentro;
    }

    public Date getAutorizdatascadenzaSubentro() {

	return autorizdatascadenzaSubentro;
    }

    public void setAutorizdatascadenzaSubentro(Date autorizdatascadenzaSubentro) {

	this.autorizdatascadenzaSubentro = autorizdatascadenzaSubentro;
    }

    public VwEntilocali getAutorizcomuneSubentro() {

	return autorizcomuneSubentro;
    }

    public void setAutorizcomuneSubentro(VwEntilocali autorizcomuneSubentro) {

	this.autorizcomuneSubentro = autorizcomuneSubentro;
    }

    public Tipologiaregistri getAutorizregistroSubentro() {

	return autorizregistroSubentro;
    }

    public void setAutorizregistroSubentro(Tipologiaregistri autorizregistroSubentro) {

	this.autorizregistroSubentro = autorizregistroSubentro;
    }

    public Date getDataRilascioSubentro() {

	return dataRilascioSubentro;
    }

    public void setDataRilascioSubentro(Date dataRilascioSubentro) {

	this.dataRilascioSubentro = dataRilascioSubentro;
    }

    public void setDataCessazione(Date dataCessazione) {

	this.dataCessazione = dataCessazione;
    }

    public Date getDataCessazione() {

	return dataCessazione;
    }

    public void setDaSubentrare(boolean daSubentrare) {

	this.daSubentrare = daSubentrare;
    }

    public boolean isDaSubentrare() {

	return daSubentrare;
    }

    public void setConcessione(AutorizzazioniConcessioni concessione) {

	this.concessione = concessione;
    }

    public AutorizzazioniConcessioni getConcessione() {

	return concessione;
    }

    public void setAutorizzazioneCollegataHelper(AutorizzazioniHelper autorizzazioneCollegataHelper) {

	this.autorizzazioneCollegataHelper = autorizzazioneCollegataHelper;
    }

    public AutorizzazioniHelper getAutorizzazioneCollegataHelper() {

	return autorizzazioneCollegataHelper;
    }

    public void setRegistroAutomatico(boolean registroAutomatico) {

	this.registroAutomatico = registroAutomatico;
    }

    public boolean isRegistroAutomatico() {

	return registroAutomatico;
    }

    public boolean isFlagMantieniNumero() {

	return flagMantieniNumero;
    }

    public void setFlagMantieniNumero(boolean flagMantieniNumero) {

	this.flagMantieniNumero = flagMantieniNumero;
    }

    public boolean isFlagAffitto() {

	return flagAffitto;
    }

    public void setFlagAffitto(boolean flagAffitto) {

	this.flagAffitto = flagAffitto;
    }
}
