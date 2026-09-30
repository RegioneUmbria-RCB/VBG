package it.gruppoinit.faldonetelematico.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "urn_allegato", propOrder = { "descrizione", "filename", "progressivo", "desc" })
public class UrnAllegato {

    @XmlAttribute(name = "descrizione")
    String descrizione;
    @XmlAttribute(name = "filename")
    String filename;
    @XmlAttribute(name = "progressivo")
    String progressivo;
    @XmlElement(name = "urn_allegato")
    String desc;

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
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