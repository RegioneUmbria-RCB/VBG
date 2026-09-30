package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiferimentoAvviso", propOrder = { "idDominio", "numeroAvviso", "idDebitore" })
public class RiferimentoAvviso {

    @XmlElement(name = "idDominio")
    private String idDominio;
    @XmlElement(name = "numeroAvviso")
    private String numeroAvviso;
    @XmlElement(name = "idDebitore")
    private String idDebitore;

    public String getIdDominio() {

	return idDominio;
    }

    public void setIdDominio(String idDominio) {

	this.idDominio = idDominio;
    }

    public String getNumeroAvviso() {

	return numeroAvviso;
    }

    public void setNumeroAvviso(String numeroAvviso) {

	this.numeroAvviso = numeroAvviso;
    }

    public String getIdDebitore() {

	return idDebitore;
    }

    public void setIdDebitore(String idDebitore) {

	this.idDebitore = idDebitore;
    }
}
