package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getLocalizzazioniAutorizzazioni")
public class GetLocalizzazioniAutorizzazioniRequest {

    @XmlElement(name = "idAutorizzazioni")
    private List<Integer> idAutorizzazioni;

    public List<Integer> getIdAutorizzazioni() {

	return idAutorizzazioni;
    }

    public void setIdAutorizzazioni(List<Integer> idAutorizzazioni) {

	this.idAutorizzazioni = idAutorizzazioni;
    }
}
