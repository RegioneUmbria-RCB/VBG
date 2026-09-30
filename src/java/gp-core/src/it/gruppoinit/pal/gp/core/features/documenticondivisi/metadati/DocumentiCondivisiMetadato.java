package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
public class DocumentiCondivisiMetadato implements Comparable<DocumentiCondivisiMetadato> {

    @XmlElement()
    private String chiave;
    @XmlValue()
    private String valore;

    public DocumentiCondivisiMetadato() {

    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public DocumentiCondivisiMetadato(String chiave, String valore) {

	this.chiave = chiave;
	this.valore = valore;
    }

    public DocumentiCondivisiMetadato(String chiave, Integer valore) {

	this.chiave = chiave;
	if (valore != null) {
	    this.valore = valore.toString();
	}
    }

    public DocumentiCondivisiMetadato(String chiave, Date valore) {

	this.chiave = chiave;
	if (valore != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    this.valore = sdf.format(valore);
	}
    }

    public String getChiave() {

	return chiave;
    }

    public String getValore() {

	return valore;
    }

    @Override
    public int compareTo(DocumentiCondivisiMetadato o) {

	if (o == null) {
	    return 1;
	}
	return this.chiave.compareTo(o.chiave);
    }
}
