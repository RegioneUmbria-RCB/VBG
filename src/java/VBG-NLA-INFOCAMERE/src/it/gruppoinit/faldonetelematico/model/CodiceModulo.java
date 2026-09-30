package it.gruppoinit.faldonetelematico.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "codice_modulo", propOrder = { "filename", "descrizione" })
public class CodiceModulo {

    @XmlAttribute(name = "filename")
    String filename;
    @XmlElement(name = "codice_modulo")
    String descrizione;

    public void setFilename(String filename) {

	this.filename = filename;
    }

    public String getFilename() {

	return filename;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }
}