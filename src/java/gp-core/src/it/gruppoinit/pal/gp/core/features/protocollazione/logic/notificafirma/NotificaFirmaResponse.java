package it.gruppoinit.pal.gp.core.features.protocollazione.logic.notificafirma;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class NotificaFirmaResponse {

    @XmlElement(name = "esito")
    private String esito;
    @XmlElement(name = "descrizione")
    private String descrizione;

    public String getEsito() {

	return esito;
    }

    public void setEsito(String esito) {

	this.esito = esito;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @Override
    public String toString() {

	return "Esito: " + this.getEsito() + ", Descrizione: " + this.getDescrizione();
    }
}
