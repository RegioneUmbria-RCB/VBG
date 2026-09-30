package it.gruppoinit.faldonetelematico.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "urn_modulo", propOrder = { "filename", "subUrn", "descrizione" })
public class UrnModulo {

    @XmlAttribute(name = "filename")
    String filename;
    @XmlAttribute(name = "sub_urn")
    String subUrn;
    @XmlElement(name = "urn_modulo")
    String descrizione;

    public void setFilename(String filename) {

	this.filename = filename;
    }

    public String getFilename() {

	return filename;
    }

    public void setSub_urn(String sub_urn) {

	this.subUrn = sub_urn;
    }

    public String getSub_urn() {

	return subUrn;
    }

    public void setTextContent(String textContent) {

	this.descrizione = textContent;
    }

    public String getTextContent() {

	return descrizione;
    }
}