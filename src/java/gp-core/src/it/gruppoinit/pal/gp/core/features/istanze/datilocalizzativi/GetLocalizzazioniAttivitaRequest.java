package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getLocalizzazioniAttivita")
public class GetLocalizzazioniAttivitaRequest {

    @XmlElement(name = "idAttivita")
    private List<Integer> idAttivita;

    public List<Integer> getIdAttivita() {

	return idAttivita;
    }

    public void setIdAttivita(List<Integer> idAttivita) {

	this.idAttivita = idAttivita;
    }
}
