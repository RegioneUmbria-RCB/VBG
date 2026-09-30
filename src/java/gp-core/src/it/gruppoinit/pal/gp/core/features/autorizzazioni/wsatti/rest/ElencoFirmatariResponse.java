package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class ElencoFirmatariResponse {

    public ElencoFirmatariResponse() {

	// TODO Auto-generated constructor stub
    }

    public ElencoFirmatariResponse(Set<FirmatarioResponse> firmatari) {

	this.firmatari = firmatari;
    }

    @XmlElement(name = "firmatari")
    private Set<FirmatarioResponse> firmatari;

    public Set<FirmatarioResponse> getFirmatari() {

	return firmatari;
    }

    public void setFirmatari(Set<FirmatarioResponse> firmatari) {

	this.firmatari = firmatari;
    }
}
