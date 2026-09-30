package it.gruppoinit.pal.gp.backoffice.schemas.messages.attivita;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType()
public class SchedaDinamicaIstanzaEliminataRequest {

    @XmlElement(required = true, type = String.class)
    protected String token;
    @XmlElement(required = true, type = Integer.class)
    protected int codiceIstanza;
    @XmlElement(required = true, type = Integer.class)
    protected int codiceScheda;

    public String getToken() {

	return token;
    }

    public void setToken(String token) {

	this.token = token;
    }

    public int getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(int codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public int getCodiceScheda() {

	return codiceScheda;
    }

    public void setCodiceScheda(int codiceScheda) {

	this.codiceScheda = codiceScheda;
    }
}
