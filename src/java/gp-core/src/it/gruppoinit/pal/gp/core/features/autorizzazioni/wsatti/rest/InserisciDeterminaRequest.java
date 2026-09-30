package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.ConfigurazioneWSAtti;

@XmlRootElement()
public class InserisciDeterminaRequest {

    @XmlElement(name = "oggetto")
    private String oggetto;
    @XmlElement(name = "trattamento")
    private String trattamento;
    @XmlElement(name = "proponente")
    private String proponente;
    @XmlElement(name = "dirigente")
    private String dirigente;
    @XmlElement(name = "data")
    private Date data;
    @XmlElement(name = "classifica")
    private String classifica;
    @XmlElement(name = "pubblicare")
    private boolean pubblicare;
    @XmlElement(name = "note")
    private String note;
    @XmlElement(name = "ruolo")
    private String ruolo;

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    public String getTrattamento() {

	return trattamento;
    }

    public void setTrattamento(String trattamento) {

	this.trattamento = trattamento;
    }

    public String getProponente() {

	return proponente;
    }

    public void setProponente(String proponente) {

	this.proponente = proponente;
    }

    public String getDirigente() {

	return dirigente;
    }

    public void setDirigente(String dirigente) {

	this.dirigente = dirigente;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getClassifica() {

	return classifica;
    }

    public void setClassifica(String classifica) {

	this.classifica = classifica;
    }

    public boolean isPubblicare() {

	return pubblicare;
    }

    public void setPubblicare(boolean pubblicare) {

	this.pubblicare = pubblicare;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public String getRuolo() {

	return ruolo;
    }

    public void setRuolo(String ruolo) {

	this.ruolo = ruolo;
    }

    public static InserisciDeterminaRequest fromConfigurazione(ConfigurazioneWSAtti config) {

	InserisciDeterminaRequest request = new InserisciDeterminaRequest();
	request.setClassifica(config.getClassifica());
	request.setData(GregorianCalendar.getInstance().getTime());
	request.setDirigente(config.getCodiceDirigente());
	request.setNote(null);
	request.setOggetto(null);
	request.setProponente(config.getCodiceProponente());
	request.setPubblicare(false);
	request.setTrattamento(config.getTrattamento());
	request.setRuolo(config.getRuolo());
	return request;
    }
}
