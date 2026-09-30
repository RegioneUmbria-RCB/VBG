package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "esito_registrazione_iuv")
public class RegistraIUVResponseType {

    @XmlElement(name = "errore")
    private String errore;

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }
}
