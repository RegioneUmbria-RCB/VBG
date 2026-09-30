package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "oneri")
public class OneriBean {

    @XmlElement(name = "importo")
    private double importo;
    @XmlElement(name = "causale")
    private String causale;
    @XmlElement(name = "note")
    private String note;

    public double getImporto() {

	return importo;
    }

    public void setImporto(double importo) {

	this.importo = importo;
    }

    public String getCausale() {

	return causale;
    }

    public void setCausale(String causale) {

	this.causale = causale;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }
}
