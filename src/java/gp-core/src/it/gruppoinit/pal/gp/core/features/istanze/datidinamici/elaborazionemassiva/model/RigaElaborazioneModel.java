package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.ws.model.EsitoElaborazioneMassivaSchedeEnum;

public class RigaElaborazioneModel implements Serializable {

    private static final long serialVersionUID = 2998321154886580527L;
    private Integer codiceistanza;
    private String numeroIstanza;
    private String richiedente;
    private String azienda;
    private String inqualita;
    private Date dataistanza;
    private String comune;
    private String intervento;
    private String statoElaborazione;
    private String log;

    public RigaElaborazioneModel() {

    }

    @XmlElement(name = "codice_istanza")
    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    @XmlElement(name = "numero_istanza")
    public String getNumeroIstanza() {

	return numeroIstanza;
    }

    public void setNumeroIstanza(String numeroIstanza) {

	this.numeroIstanza = numeroIstanza;
    }

    @XmlElement(name = "richiedente")
    public String getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(String richiedente) {

	this.richiedente = richiedente;
    }

    @XmlElement(name = "azienda")
    public String getAzienda() {

	return azienda;
    }

    public void setAzienda(String azienda) {

	this.azienda = azienda;
    }

    @XmlElement(name = "in_qualita_di")
    public String getInqualita() {

	return inqualita;
    }

    public void setInqualita(String inqualita) {

	this.inqualita = inqualita;
    }

    @XmlElement(name = "data_istanza")
    public Date getDataistanza() {

	return dataistanza;
    }

    public void setDataistanza(Date dataistanza) {

	this.dataistanza = dataistanza;
    }

    @XmlElement(name = "comune")
    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    @XmlElement(name = "intervento")
    public String getIntervento() {

	return intervento;
    }

    public void setIntervento(String intervento) {

	this.intervento = intervento;
    }

    @XmlElement(name = "stato_elaborazione")
    public String getStatoElaborazione() {

	if (StringUtils.isEmpty(this.statoElaborazione)) {
	    return "";
	}
	switch (EsitoElaborazioneMassivaSchedeEnum.fromValue(this.statoElaborazione)) {
	case ELABORAZIONE_IN_CORSO: {
	    return "Elaborazione in corso";
	}
	case ELABORAZIONE_COMPLETATA_CON_SUCCESSO: {
	    return "Elaborata con successo";
	}
	case ELABORAZIONE_COMPLETATA_CON_ERRORI: {
	    return "Errore in fase di elaborazione";
	}
	default: {
	    return "Pronta per elaborazione";
	}
	}
    }

    public void setStatoElaborazione(String statoElaborazione) {

	this.statoElaborazione = statoElaborazione;
    }

    @XmlElement(name = "log")
    public String getLog() {

	return log;
    }

    public void setLog(String log) {

	this.log = log;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((numeroIstanza == null) ? 0 : numeroIstanza.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	RigaElaborazioneModel other = (RigaElaborazioneModel) obj;
	if (numeroIstanza == null) {
	    if (other.numeroIstanza != null) {
		return false;
	    }
	} else if (!numeroIstanza.equals(other.numeroIstanza))
	    return false;
	return true;
    }
}
