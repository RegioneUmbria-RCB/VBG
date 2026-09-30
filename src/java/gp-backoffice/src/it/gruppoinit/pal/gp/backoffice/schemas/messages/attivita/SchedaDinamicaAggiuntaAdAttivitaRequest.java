package it.gruppoinit.pal.gp.backoffice.schemas.messages.attivita;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType()
public class SchedaDinamicaAggiuntaAdAttivitaRequest {

    @XmlElement(required = true, type = String.class)
    protected String token;
    @XmlElement(required = true, type = Integer.class)
    protected int codiceAttivita;
    @XmlElement(required = true, type = Integer.class)
    protected int codiceScheda;

    public String getToken() {

	return token;
    }

    public void setToken(String token) {

	this.token = token;
    }

    public int getCodiceAttivita() {

	return codiceAttivita;
    }

    public void setCodiceAttivita(int codiceAttivita) {

	this.codiceAttivita = codiceAttivita;
    }

    public int getCodiceScheda() {

	return codiceScheda;
    }

    public void setCodiceScheda(int codiceScheda) {

	this.codiceScheda = codiceScheda;
    }
}
