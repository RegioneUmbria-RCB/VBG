package it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class MercatoSrvRequest {

    private List<String> codiceFiscale;
    private List<String> autorizzazione;
    private String comuni;
    private boolean soloAttivi;

    public MercatoSrvRequest(List<String> pCodiceFiscale, List<String> pAutorizzazione, String comuni, boolean soloAttivi) {

	super();
	this.codiceFiscale = new ArrayList<String>();
	this.autorizzazione = new ArrayList<String>();
	if (pCodiceFiscale != null) {
	    for (String cf : pCodiceFiscale) {
		if (StringUtils.isNotBlank(cf)) {
		    this.codiceFiscale.add(cf.trim());
		}
	    }
	}
	if (pAutorizzazione != null) {
	    for (String aut : pAutorizzazione) {
		if (StringUtils.isNotBlank(aut)) {
		    this.autorizzazione.add(aut.trim());
		}
	    }
	}
	this.comuni = comuni;
	this.soloAttivi = soloAttivi;
    }

    public List<String> getCodiceFiscale() {

	if (this.codiceFiscale == null) {
	    this.codiceFiscale = new ArrayList<String>();
	}
	return codiceFiscale;
    }

    public void setCodiceFiscale(List<String> codiceFiscale) {

	this.codiceFiscale = codiceFiscale;
    }

    public List<String> getAutorizzazione() {

	if (this.autorizzazione == null) {
	    this.autorizzazione = new ArrayList<String>();
	}
	return autorizzazione;
    }

    public void setAutorizzazione(List<String> autorizzazione) {

	this.autorizzazione = autorizzazione;
    }

    public String getComuni() {

	return comuni;
    }

    public void setComuni(String comuni) {

	this.comuni = comuni;
    }

    public boolean isSoloAttivi() {

	return soloAttivi;
    }

    public void setSoloAttivi(boolean soloAttivi) {

	this.soloAttivi = soloAttivi;
    }

    public Set<String> getListaCfUnivoci() {

	if (getCodiceFiscale().isEmpty()) {
	    return new HashSet<String>(0);
	}
	Set<String> ret = new HashSet<String>();
	for (String cf : getCodiceFiscale()) {
	    cf = StringUtils.defaultString(cf).trim().toUpperCase();
	    if (StringUtils.isNotBlank(cf)) {
		ret.add(cf);
	    }
	}
	return ret;
    }

    public List<String> validaRequest() {

	List<String> errori = new ArrayList<String>();
	if (getListaCfUnivoci().isEmpty() && getAutorizzazione().isEmpty()) {
	    errori.add("E' necessario specificare almeno uno dei parametri Codici Fiscali o numero autorizzazioni");
	}
	if (getListaCfUnivoci().size() > 8) {
	    errori.add("Sono stati specificati più di 8 codici fiscali");
	}
	return errori;
    }

    public String getErroriToString() {

	StringBuilder sb = new StringBuilder();
	sb.append("Si sono verificati i seguenti errori di validazione: ");
	List<String> errori = validaRequest();
	for (String string : errori) {
	    sb.append("\n- ").append(string);
	}
	return sb.toString();
    }
}
