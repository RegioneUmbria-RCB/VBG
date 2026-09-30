package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AppIoCodaRestBean {

    @XmlElement(name = "guid")
    private String guid;
    @XmlElement(name = "id_servizio")
    private String idServizio;
    @XmlElement(name = "oggetto")
    private String oggetto;
    @XmlElement(name = "messaggio")
    private String messaggio;
    @XmlElement(name = "codice_fiscale")
    private String codiceFiscale;
    @XmlElement(name = "stato_messaggio")
    private String statoMessaggio;
    @XmlElement(name = "ultimo_stato")
    private String ultimoStato;
    @XmlElement(name = "ultimo_datastato")
    private Date ultimoDataStato;

    public String getGuid() {

	return guid;
    }

    public void setGuid(String guid) {

	this.guid = guid;
    }

    public String getIdServizio() {

	return idServizio;
    }

    public void setIdServizio(String idServizio) {

	this.idServizio = idServizio;
    }

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }

    public String getCodiceFiscale() {

	return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {

	this.codiceFiscale = codiceFiscale;
    }

    public String getStatoMessaggio() {

	return statoMessaggio;
    }

    public void setStatoMessaggio(String statoMessaggio) {

	this.statoMessaggio = statoMessaggio;
    }

    public String getUltimoStato() {

	return ultimoStato;
    }

    public void setUltimoStato(String ultimoStato) {

	this.ultimoStato = ultimoStato;
    }

    public Date getUltimoDataStato() {

	return ultimoDataStato;
    }

    public void setUltimoDataStato(Date ultimoDataStato) {

	this.ultimoDataStato = ultimoDataStato;
    }
}
