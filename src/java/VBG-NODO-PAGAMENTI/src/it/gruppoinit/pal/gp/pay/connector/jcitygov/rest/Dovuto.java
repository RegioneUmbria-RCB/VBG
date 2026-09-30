package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Dovuto {

    @XmlElement
    private String contestoDovuto;

    @XmlElement
    private List<DettaglioDovuto> dettaglioDovuto;

    @XmlElement
    private NumeroAvviso numeroAvviso;

    @XmlElement
    private TestataDovuto testataDovuto;

	public String getContestoDovuto() {
		return contestoDovuto;
	}

	public void setContestoDovuto(String contestoDovuto) {
		this.contestoDovuto = contestoDovuto;
	}

	public List<DettaglioDovuto> getDettaglioDovuto() {
		return dettaglioDovuto;
	}

	public void setDettaglioDovuto(List<DettaglioDovuto> dettaglioDovuto) {
		this.dettaglioDovuto = dettaglioDovuto;
	}

	public NumeroAvviso getNumeroAvviso() {
		return numeroAvviso;
	}

	public void setNumeroAvviso(NumeroAvviso numeroAvviso) {
		this.numeroAvviso = numeroAvviso;
	}

	public TestataDovuto getTestataDovuto() {
		return testataDovuto;
	}

	public void setTestataDovuto(TestataDovuto testataDovuto) {
		this.testataDovuto = testataDovuto;
	}

    
}
