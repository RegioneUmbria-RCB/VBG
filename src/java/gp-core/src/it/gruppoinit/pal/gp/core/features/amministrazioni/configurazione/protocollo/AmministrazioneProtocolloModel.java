package it.gruppoinit.pal.gp.core.features.amministrazioni.configurazione.protocollo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dettaglio")
@XmlAccessorType(XmlAccessType.FIELD)
public class AmministrazioneProtocolloModel {

    @XmlElement(name = "codice")
    private Integer codice;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "uo")
    private String uo;
    @XmlElement(name = "ruolo")
    private String ruolo;

    public AmministrazioneProtocolloModel() {

	// TODO Auto-generated constructor stub
    }

    public AmministrazioneProtocolloModel(Integer codice, String descrizione, String uo, String ruolo) {

	this.codice = codice;
	this.descrizione = descrizione;
	this.uo = uo;
	this.ruolo = ruolo;
    }

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getUo() {

	return uo;
    }

    public void setUo(String uo) {

	this.uo = uo;
    }

    public String getRuolo() {

	return ruolo;
    }

    public void setRuolo(String ruolo) {

	this.ruolo = ruolo;
    }
}
