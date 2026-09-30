package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import javax.xml.bind.annotation.XmlElement;

public class SoggettoPagatore {

    @XmlElement(name = "tipoIdentificativoUnivocoPagatore")
    private String tipoIdentificativoUnivocoPagatore;
    @XmlElement(name = "codiceIdentificativoUnivocoPagatore")
    private String codiceIdentificativoUnivocoPagatore;
    @XmlElement(name = "anagraficaPagatore")
    private String anagraficaPagatore;
    @XmlElement(name = "indirizzoPagatore", required = false)
    private String indirizzoPagatore;
    @XmlElement(name = "civicoPagatore", required = false)
    private String civicoPagatore;
    @XmlElement(name = "capPagatore", required = false)
    private String capPagatore;
    @XmlElement(name = "localitaPagatore", required = false)
    private String localitaPagatore;
    @XmlElement(name = "provinciaPagatore", required = false)
    private String provinciaPagatore;
    @XmlElement(name = "nazionePagatore", required = false)
    private String nazionePagatore;
    @XmlElement(name = "emailPagatore", required = false)
    private String emailPagatore;

    public String getTipoIdentificativoUnivocoPagatore() {

	return tipoIdentificativoUnivocoPagatore;
    }

    public void setTipoIdentificativoUnivocoPagatore(String tipoIdentificativoUnivocoPagatore) {

	this.tipoIdentificativoUnivocoPagatore = tipoIdentificativoUnivocoPagatore;
    }

    public String getCodiceIdentificativoUnivocoPagatore() {

	return codiceIdentificativoUnivocoPagatore;
    }

    public void setCodiceIdentificativoUnivocoPagatore(String codiceIdentificativoUnivocoPagatore) {

	this.codiceIdentificativoUnivocoPagatore = codiceIdentificativoUnivocoPagatore;
    }

    public String getAnagraficaPagatore() {

	return anagraficaPagatore;
    }

    public void setAnagraficaPagatore(String anagraficaPagatore) {

	this.anagraficaPagatore = anagraficaPagatore;
    }

    public String getIndirizzoPagatore() {

	return indirizzoPagatore;
    }

    public void setIndirizzoPagatore(String indirizzoPagatore) {

	this.indirizzoPagatore = indirizzoPagatore;
    }

    public String getCivicoPagatore() {

	return civicoPagatore;
    }

    public void setCivicoPagatore(String civicoPagatore) {

	this.civicoPagatore = civicoPagatore;
    }

    public String getCapPagatore() {

	return capPagatore;
    }

    public void setCapPagatore(String capPagatore) {

	this.capPagatore = capPagatore;
    }

    public String getLocalitaPagatore() {

	return localitaPagatore;
    }

    public void setLocalitaPagatore(String localitaPagatore) {

	this.localitaPagatore = localitaPagatore;
    }

    public String getProvinciaPagatore() {

	return provinciaPagatore;
    }

    public void setProvinciaPagatore(String provinciaPagatore) {

	this.provinciaPagatore = provinciaPagatore;
    }

    public String getNazionePagatore() {

	return nazionePagatore;
    }

    public void setNazionePagatore(String nazionePagatore) {

	this.nazionePagatore = nazionePagatore;
    }

    public String getEmailPagatore() {

	return emailPagatore;
    }

    public void setEmailPagatore(String emailPagatore) {

	this.emailPagatore = emailPagatore;
    }
}
