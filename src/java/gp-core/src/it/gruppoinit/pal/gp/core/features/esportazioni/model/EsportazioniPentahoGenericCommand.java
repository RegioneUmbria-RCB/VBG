package it.gruppoinit.pal.gp.core.features.esportazioni.model;

public class EsportazioniPentahoGenericCommand {

    private EsportazioniPentahoEsportazioneModel esportazioni;
    private EsportazioniPentahoResponsabili responsabili;
    private String parametroCodiceEsportazione;
    private String emailResponsabile;
    private Integer idAccount;
    private Boolean invioMail;

    public String getParametroCodiceEsportazione() {

	return parametroCodiceEsportazione;
    }

    public void setParametroCodiceEsportazione(String parametroCodiceEsportazione) {

	this.parametroCodiceEsportazione = parametroCodiceEsportazione;
    }

    public EsportazioniPentahoEsportazioneModel getEsportazioni() {

	return esportazioni;
    }

    public void setEsportazioni(EsportazioniPentahoEsportazioneModel esportazioni) {

	this.esportazioni = esportazioni;
    }

    public EsportazioniPentahoResponsabili getResponsabili() {

	return responsabili;
    }

    public void setResponsabili(EsportazioniPentahoResponsabili responsabili) {

	this.responsabili = responsabili;
    }

    public String getEmailResponsabile() {

	return emailResponsabile;
    }

    public void setEmailResponsabile(String emailResponsabile) {

	this.emailResponsabile = emailResponsabile;
    }

    public Integer getIdAccount() {

	return idAccount;
    }

    public void setIdAccount(Integer idAccount) {

	this.idAccount = idAccount;
    }

    public Boolean getInvioMail() {

	return invioMail;
    }

    public void setInvioMail(Boolean invioMail) {

	this.invioMail = invioMail;
    }
}
