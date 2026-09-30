package it.gruppoinit.pal.gp.core.features.metadati;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "metadato")
@XmlAccessorType(XmlAccessType.FIELD)
public class Metadato {

    @XmlElement(name = "chiave")
    private String chiave;
    @XmlElement(name = "valore")
    private String valore;

    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
