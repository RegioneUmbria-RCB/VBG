package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseDTO", propOrder = { "codice", "stato", "messaggioErrore" })
public class ResponseDTO {

    @XmlElement(name = "codice")
    private String codice;
    @XmlElement(name = "stato")
    private String stato;
    @XmlElement(name = "errore")
    private String messaggioErrore;

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getMessaggioErrore() {

	return messaggioErrore;
    }

    public void setMessaggioErrore(String messaggioErrore) {

	this.messaggioErrore = messaggioErrore;
    }
}
