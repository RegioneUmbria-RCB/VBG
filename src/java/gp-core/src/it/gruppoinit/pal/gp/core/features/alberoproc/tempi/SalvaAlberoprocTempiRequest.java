package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalvaAlberoprocTempiRequest {

    @XmlElement(name = "codiceintervento")
    private Integer codiceIntervento;
    @XmlElement(name = "idtempot")
    private Integer idTempot;
    @XmlElement(name = "desctempot")
    private String descTempot;
    @XmlElement(name = "dettaglio")
    private List<TempoFoDModel> dettaglio = new ArrayList<TempoFoDModel>();

    public Integer getCodiceIntervento() {

	return codiceIntervento;
    }

    public void setCodiceIntervento(Integer codiceIntervento) {

	this.codiceIntervento = codiceIntervento;
    }

    public Integer getIdTempot() {

	return idTempot;
    }

    public void setIdTempot(Integer idTempot) {

	this.idTempot = idTempot;
    }

    public String getDescTempot() {

	return descTempot;
    }

    public void setDescTempot(String descTempot) {

	this.descTempot = descTempot;
    }

    public List<TempoFoDModel> getDettaglio() {

	return dettaglio;
    }

    public void setDettaglio(List<TempoFoDModel> dettaglio) {

	this.dettaglio = dettaglio;
    }
}
