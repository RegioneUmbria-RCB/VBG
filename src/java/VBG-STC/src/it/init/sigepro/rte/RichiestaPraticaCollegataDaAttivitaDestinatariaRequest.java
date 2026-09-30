package it.init.sigepro.rte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.init.sigepro.rte.types.SportelloType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "token", "sportello", "idAttivita" })
@XmlRootElement(name = "RichiestaPraticaCollegataDaAttivitaDestinatariaRequest")
public class RichiestaPraticaCollegataDaAttivitaDestinatariaRequest {

    @XmlElement(required = true)
    private String token;
    @XmlElement(required = true)
    private SportelloType sportello;
    @XmlElement(required = true)
    private String idAttivita;

    public String getToken() {

	return token;
    }

    public void setToken(String token) {

	this.token = token;
    }

    public SportelloType getSportello() {

	return sportello;
    }

    public void setSportello(SportelloType sportello) {

	this.sportello = sportello;
    }

    public String getIdAttivita() {

	return idAttivita;
    }

    public void setIdAttivita(String idAttivita) {

	this.idAttivita = idAttivita;
    }
}
