package it.gruppoinit.faldonetelematico.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "codice_allegato", propOrder = { "descrizione", "descrizioneFissa", "descrizioneUtente", "filename", "progressivo", "desc" })
public class CodiceAllegato {

    @XmlAttribute(name = "descrizione")
    String descrizione;
    @XmlAttribute(name = "descrizione_fissa")
    String descrizioneFissa;
    @XmlAttribute(name = "descrizione_utente")
    String descrizioneUtente;
    @XmlAttribute(name = "filename")
    String filename;
    @XmlAttribute(name = "progressivo")
    String progressivo;
    @XmlElement(name = "codice_allegato")
    String desc;

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getDescrizioneFissa() {

	return descrizioneFissa;
    }

    public void setDescrizioneFissa(String descrizioneFissa) {

	this.descrizioneFissa = descrizioneFissa;
    }

    public String getDescrizioneUtente() {

	return descrizioneUtente;
    }

    public void setDescrizioneUtente(String descrizioneUtente) {

	this.descrizioneUtente = descrizioneUtente;
    }

    public String getFilename() {

	return filename;
    }

    public void setFilename(String filename) {

	this.filename = filename;
    }

    public String getProgressivo() {

	return progressivo;
    }

    public void setProgressivo(String progressivo) {

	this.progressivo = progressivo;
    }

    public String getDesc() {

	return desc;
    }

    public void setDesc(String desc) {

	this.desc = desc;
    }
}