package it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StampaPDFRiferimentiDocumento {

    @XmlElement
    private String idDocumento;
    @XmlElement
    private String dataDocumento;
    @XmlElement
    private String oraDocumento;
    @XmlElement
    private String codiceFiscale;
    @XmlElement
    private String nominativo;

    public String getIdDocumento() {

	return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {

	this.idDocumento = idDocumento;
    }

    public String getDataDocumento() {

	return dataDocumento;
    }

    public void setDataDocumento(String dataDocumento) {

	this.dataDocumento = dataDocumento;
    }

    public String getOraDocumento() {

	return oraDocumento;
    }

    public void setOraDocumento(String oraDocumento) {

	this.oraDocumento = oraDocumento;
    }

    public String getCodiceFiscale() {

	return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {

	this.codiceFiscale = codiceFiscale;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }
}
