package it.gruppoinit.domain.helper;

import it.gruppoinit.service.DeployProperties;

public class ParametriHelper {

    private VerticalizzazioniHelper verticalizzazioniHelper;
    private String codificaNotificaTipoOperazione;
    private String descrizioneNotificaTipoOperazione;
    private String enteBackoffice;
    private String sportelloBackoffice;
    private String tokenBackoffice;
    private String tokenStc;
    private DeployProperties deployProperties;

    public VerticalizzazioniHelper getVerticalizzazioniHelper() {

	return verticalizzazioniHelper;
    }

    public void setVerticalizzazioniHelper(VerticalizzazioniHelper verticalizzazioniHelper) {

	this.verticalizzazioniHelper = verticalizzazioniHelper;
    }

    public String getCodificaNotificaTipoOperazione() {

	return codificaNotificaTipoOperazione;
    }

    public void setCodificaNotificaTipoOperazione(String codificaNotificaTipoOperazione) {

	this.codificaNotificaTipoOperazione = codificaNotificaTipoOperazione;
    }

    public String getDescrizioneNotificaTipoOperazione() {

	return descrizioneNotificaTipoOperazione;
    }

    public void setDescrizioneNotificaTipoOperazione(String descrizioneNotificaTipoOperazione) {

	this.descrizioneNotificaTipoOperazione = descrizioneNotificaTipoOperazione;
    }

    public String getEnteBackoffice() {

	return enteBackoffice;
    }

    public void setEnteBackoffice(String enteBackoffice) {

	this.enteBackoffice = enteBackoffice;
    }

    public String getSportelloBackoffice() {

	return sportelloBackoffice;
    }

    public void setSportelloBackoffice(String sportelloBackoffice) {

	this.sportelloBackoffice = sportelloBackoffice;
    }

    public String getTokenBackoffice() {

	return tokenBackoffice;
    }

    public void setTokenBackoffice(String tokenBackoffice) {

	this.tokenBackoffice = tokenBackoffice;
    }

    public String getTokenStc() {

	return tokenStc;
    }

    public void setTokenStc(String tokenStc) {

	this.tokenStc = tokenStc;
    }

    public DeployProperties getDeployProperties() {

	return deployProperties;
    }

    public void setDeployProperties(DeployProperties deployProperties) {

	this.deployProperties = deployProperties;
    }
}
