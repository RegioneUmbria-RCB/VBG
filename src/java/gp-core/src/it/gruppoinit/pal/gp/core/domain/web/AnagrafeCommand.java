package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.domain.Emailanagr;
import it.gruppoinit.pal.gp.core.domain.Scadenze;

import org.apache.commons.lang.BooleanUtils;

public class AnagrafeCommand extends BaseCommand {

    private Anagrafe entity;
    private Scadenze scadenze;
    private Emailanagr emailanagr;
    private Anagrafedocumenti anagrafedocumenti;
    // il campo è utilizzato nella funzionalità controlla aggoirnamaneti, serve per mostrare il nuovo vaore proposto
    private Anagrafe oldAnagrafe;
    private Boolean popup;
    private String popupCaller;
    private String cfPivaRicercaWs;
    // Utilizzato per dicidere quale tipo anagrafe mostrare alla creazione (Tutti,giuridica,fisica)
    private String visualizzaTipoAnagrafe;

    public AnagrafeCommand() {

	super();
	this.entity = new Anagrafe();
	this.scadenze = new Scadenze();
	this.emailanagr = new Emailanagr();
	this.anagrafedocumenti = new Anagrafedocumenti();
	this.oldAnagrafe = new Anagrafe();
    }

    public Anagrafe getEntity() {

	return entity;
    }

    public void setEntity(Anagrafe entity) {

	this.entity = entity;
    }

    public Scadenze getScadenze() {

	return scadenze;
    }

    public void setScadenze(Scadenze scadenze) {

	this.scadenze = scadenze;
    }

    public Emailanagr getEmailanagr() {

	return emailanagr;
    }

    public void setEmailanagr(Emailanagr emailanagr) {

	this.emailanagr = emailanagr;
    }

    public Anagrafedocumenti getAnagrafedocumenti() {

	return anagrafedocumenti;
    }

    public void setAnagrafedocumenti(Anagrafedocumenti anagrafedocumenti) {

	this.anagrafedocumenti = anagrafedocumenti;
    }

    public Anagrafe getOldAnagrafe() {

	return oldAnagrafe;
    }

    public void setOldAnagrafe(Anagrafe oldAnagrafe) {

	this.oldAnagrafe = oldAnagrafe;
    }

    public String getPrefixPopup() {

	return BooleanUtils.isTrue(getPopup()) == true ? "popup" : "";
    }

    public String getPopupCaller() {

	return popupCaller;
    }

    public void setPopupCaller(String popupCaller) {

	this.popupCaller = popupCaller;
    }

    public Boolean getPopup() {

	return popup;
    }

    public void setPopup(Boolean popup) {

	this.popup = popup;
    }

    public void setCfPivaRicercaWs(String cfPivaRicercaWs) {

	this.cfPivaRicercaWs = cfPivaRicercaWs;
    }

    public String getCfPivaRicercaWs() {

	return cfPivaRicercaWs;
    }

    
    public String getVisualizzaTipoAnagrafe() {
    
        return visualizzaTipoAnagrafe;
    }

    
    public void setVisualizzaTipoAnagrafe(String visualizzaTipoAnagrafe) {
    
        this.visualizzaTipoAnagrafe = visualizzaTipoAnagrafe;
    }
    
    
}
