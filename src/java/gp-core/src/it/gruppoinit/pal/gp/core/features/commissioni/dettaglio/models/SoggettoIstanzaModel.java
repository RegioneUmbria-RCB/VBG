package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models;

import javax.xml.bind.annotation.XmlElement;

public class SoggettoIstanzaModel {

    @XmlElement(name = "codiceanagrafe")
    private Integer codiceAnagrafe;
    @XmlElement(name = "soggetto")
    private String soggetto;
    @XmlElement(name = "qualifica")
    private String qualifica;
    @XmlElement(name = "associatoapratica")
    private boolean associatoAPratica;
    @XmlElement(name = "presenteinappello")
    private boolean presenteInAppello;
    @XmlElement(name = "idCarica")
    private Integer idCarica;

    public Integer getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public void setCodiceAnagrafe(Integer codiceAnagrafe) {

	this.codiceAnagrafe = codiceAnagrafe;
    }

    public String getSoggetto() {

	return soggetto;
    }

    public void setSoggetto(String soggetto) {

	this.soggetto = soggetto;
    }

    public String getQualifica() {

	return qualifica;
    }

    public void setQualifica(String qualifica) {

	this.qualifica = qualifica;
    }

    public boolean isPresenteInAppello() {

	return presenteInAppello;
    }

    public void setPresenteInAppello(boolean presenteInAppello) {

	this.presenteInAppello = presenteInAppello;
    }

    public boolean isAssociatoAPratica() {

	return associatoAPratica;
    }

    public void setAssociatoAPratica(boolean associatoAPratica) {

	this.associatoAPratica = associatoAPratica;
    }

    public Integer getIdCarica() {

	return idCarica;
    }

    public void setIdCarica(Integer idCarica) {

	this.idCarica = idCarica;
    }
}
