package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getLocalizzazioni")
public class GetLocalizzazioniRequest {

    @XmlElement(name = "uuidIstanze")
    private List<String> uuidIstanze;

    public List<String> getUuidIstanze() {

	return uuidIstanze;
    }

    public void setUuidIstanze(List<String> uuidIstanze) {

	this.uuidIstanze = uuidIstanze;
    }
}
