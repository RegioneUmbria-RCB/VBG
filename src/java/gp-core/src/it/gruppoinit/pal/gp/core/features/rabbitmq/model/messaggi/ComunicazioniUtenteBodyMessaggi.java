package it.gruppoinit.pal.gp.core.features.rabbitmq.model.messaggi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement(name = "body")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComunicazioniUtenteBodyMessaggi {

    private String uuid;
    private String titolo;
    private String sottotitolo;
    private String testo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date dataCreazione;
    private List<String> codiciFiscaliDestinatari;
    private String categoria;

    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    public String getTitolo() {

	return titolo;
    }

    public void setTitolo(String titolo) {

	this.titolo = titolo;
    }

    public String getSottotitolo() {

	return sottotitolo;
    }

    public void setSottotitolo(String sottotitolo) {

	this.sottotitolo = sottotitolo;
    }

    public String getTesto() {

	return testo;
    }

    public void setTesto(String testo) {

	this.testo = testo;
    }

    public Date getDataCreazione() {

	return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {

	this.dataCreazione = dataCreazione;
    }

    public List<String> getCodiciFiscaliDestinatari() {

	if (this.codiciFiscaliDestinatari == null) {
	    this.codiciFiscaliDestinatari = new ArrayList<String>();
	}
	return codiciFiscaliDestinatari;
    }

    public void setCodiciFiscaliDestinatari(List<String> codiciFiscaliDestinatari) {

	this.codiciFiscaliDestinatari = codiciFiscaliDestinatari;
    }

    public String getCategoria() {

	return categoria;
    }

    public void setCategoria(String categoria) {

	this.categoria = categoria;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}