package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ComuneModel {

    @XmlElement(name = "comune")
    private String comune;

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }
}
