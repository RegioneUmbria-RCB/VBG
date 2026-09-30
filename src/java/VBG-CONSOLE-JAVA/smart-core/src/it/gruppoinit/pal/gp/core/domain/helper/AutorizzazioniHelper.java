package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.VwEntilocali;

import java.util.Date;

public class AutorizzazioniHelper {

    private String autoriznumeroSubentro;
    private Date autorizdataSubentro;
    private Date autorizdatascadenzaSubentro;
    private VwEntilocali autorizcomuneSubentro;
    private Tipologiaregistri autorizregistroSubentro;
    private Date dataCessazione;
    private boolean daSubentrare;
    private boolean registroAutomatico;

    public AutorizzazioniHelper() {

	this.autorizcomuneSubentro = new VwEntilocali();
	this.autorizregistroSubentro = new Tipologiaregistri();
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

    public void setRegistroAutomatico(boolean registroAutomatico) {

	this.registroAutomatico = registroAutomatico;
    }

    public boolean isRegistroAutomatico() {

	return registroAutomatico;
    }
}
