package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.LinkPreferitiUtente;
import it.gruppoinit.pal.gp.core.domain.Oggetti;

public class ConfigurazioniutenteCommand extends BaseCommand {

    // Permette di memorizzare uno style in configurazioen all'utente loggato
    private String styleCss;
    // Permette di memorizzare il limite dei record che ogni pagina delle liste devono visualizzare
    private String limitePaginazioneListe;
    // Permette di salvare in configurazione il comportamento desiderato dall'utente al salvataggio di un
    // movimento
    private Boolean salvaEchiudiInMovimenti;
    private LinkPreferitiUtente linkPreferitiUtente;
    private Integer userObjectId;

    public ConfigurazioniutenteCommand() {

	super();
	this.linkPreferitiUtente = new LinkPreferitiUtente();
    }

    public String getStyleCss() {

	return styleCss;
    }

    public void setStyleCss(String styleCss) {

	this.styleCss = styleCss;
    }

    public String getLimitePaginazioneListe() {

	return limitePaginazioneListe;
    }

    public void setLimitePaginazioneListe(String limitePaginazioneListe) {

	this.limitePaginazioneListe = limitePaginazioneListe;
    }

    public Boolean getSalvaEchiudiInMovimenti() {

	return salvaEchiudiInMovimenti;
    }

    public void setSalvaEchiudiInMovimenti(Boolean salvaEchiudiInMovimenti) {

	this.salvaEchiudiInMovimenti = salvaEchiudiInMovimenti;
    }

    public LinkPreferitiUtente getLinkPreferitiUtente() {

	return linkPreferitiUtente;
    }

    public void setLinkPreferitiUtente(LinkPreferitiUtente linkPreferitiUtente) {

	this.linkPreferitiUtente = linkPreferitiUtente;
    }

    public Integer getUserObjectId() {

	return userObjectId;
    }

    public void setUserObjectId(Integer objId) {

	this.userObjectId = objId;
    }
}
