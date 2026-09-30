package it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class RataResponseType {

    @XmlElement(name = "numero")
    private Integer numero;
    @XmlElement(name = "dataScadenza")
    private String dataScadenza;
    @XmlElement(name = "dettagli")
    private Set<OnereResponseType> dettagli = new HashSet<OnereResponseType>();

    public RataResponseType() {

	super();
    }

    public RataResponseType(Integer numeroRata, Date dataScadenza) {

	this.numero = numeroRata;
	this.setDataScadenza(dataScadenza);
    }

    public void setDataScadenza(Date dataScadenza) {

	if (dataScadenza == null) {
	    this.dataScadenza = "";
	    return;
	}
	SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
	this.dataScadenza = sdfr.format(dataScadenza);
    }

    @XmlTransient
    public Set<OnereResponseType> getDettagli() {

	if (dettagli == null) {
	    return new HashSet<OnereResponseType>();
	}
	return dettagli;
    }

    public void setDettagli(Set<OnereResponseType> dettagli) {

	this.dettagli = dettagli;
    }

    @XmlTransient
    public Integer getNumero() {

	return numero;
    }

    @XmlTransient
    public String getDataScadenza() {

	return dataScadenza;
    }
}
