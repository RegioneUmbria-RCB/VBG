package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "emailUtente", "identificativoUtente" })
@XmlAccessorType(XmlAccessType.FIELD)
public class UserData {

    @XmlElement(name = "EmailUtente")
    private String emailUtente;
    @XmlElement(required = true,name = "IdentificativoUtente")
    private String identificativoUtente;

    public String getEmailUtente() {

	return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {

	this.emailUtente = emailUtente;
    }

    public String getIdentificativoUtente() {

	return identificativoUtente;
    }

    public void setIdentificativoUtente(String identificativoUtente) {

	this.identificativoUtente = identificativoUtente;
    }
}
