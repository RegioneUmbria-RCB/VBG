package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class LeggiDeterminaResponse {

    @XmlElement(name = "iddocumento")
    private Integer idDocumento;
    @XmlElement(name = "codiceclassifica")
    private String codiceClassifica;
    @XmlElement(name = "classifica")
    private String classifica;
    @XmlElement(name = "numeroproposta")
    private String numeroProposta;
    @XmlElement(name = "annoproposta")
    private Integer annoProposta;
    @XmlElement(name = "ufficioproponente")
    private String ufficioProponente;
    @XmlElement(name = "strutturaproponente")
    private String strutturaProponente;
    @XmlElement(name = "dirigente")
    private String dirigente;
    @XmlElement(name = "oggetto")
    private String oggetto;
    @XmlElement(name = "numeroatto")
    private String numeroAtto;
    @XmlElement(name = "dataatto")
    private Date dataAtto;
    @XmlElement(name = "annoatto")
    private Integer annoAtto;
    @XmlElement(name = "esito")
    private Esito esito;

    public Integer getIdDocumento() {

	return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {

	this.idDocumento = idDocumento;
    }

    public Esito getEsito() {

	return esito;
    }

    public void setEsito(Esito esito) {

	this.esito = esito;
    }

    public String getCodiceClassifica() {

	return codiceClassifica;
    }

    public void setCodiceClassifica(String codiceClassifica) {

	this.codiceClassifica = codiceClassifica;
    }

    public String getClassifica() {

	return classifica;
    }

    public void setClassifica(String classifica) {

	this.classifica = classifica;
    }

    public String getNumeroProposta() {

	return numeroProposta;
    }

    public void setNumeroProposta(String numeroProposta) {

	this.numeroProposta = numeroProposta;
    }

    public Integer getAnnoProposta() {

	return annoProposta;
    }

    public void setAnnoProposta(Integer annoProposta) {

	this.annoProposta = annoProposta;
    }

    public String getUfficioProponente() {

	return ufficioProponente;
    }

    public void setUfficioProponente(String ufficioProponente) {

	this.ufficioProponente = ufficioProponente;
    }

    public String getStrutturaProponente() {

	return strutturaProponente;
    }

    public void setStrutturaProponente(String strutturaProponente) {

	this.strutturaProponente = strutturaProponente;
    }

    public String getDirigente() {

	return dirigente;
    }

    public void setDirigente(String dirigente) {

	this.dirigente = dirigente;
    }

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    public String getNumeroAtto() {

	return numeroAtto;
    }

    public void setNumeroAtto(String numeroAtto) {

	this.numeroAtto = numeroAtto;
    }

    public Date getDataAtto() {

	return dataAtto;
    }

    public void setDataAtto(Date dataAtto) {

	this.dataAtto = dataAtto;
    }

    public Integer getAnnoAtto() {

	return annoAtto;
    }

    public void setAnnoAtto(Integer annoAtto) {

	this.annoAtto = annoAtto;
    }
}
