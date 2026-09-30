package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TestataDovuto {

    @XmlElement
    private DatiContribuente datiContribuente;

    @XmlElement
    private String dettaglioPosizione;

    @XmlElement
    private String idPos;

	public DatiContribuente getDatiContribuente() {
		return datiContribuente;
	}

	public void setDatiContribuente(DatiContribuente datiContribuente) {
		this.datiContribuente = datiContribuente;
	}

	public String getDettaglioPosizione() {
		return dettaglioPosizione;
	}

	public void setDettaglioPosizione(String dettaglioPosizione) {
		this.dettaglioPosizione = dettaglioPosizione;
	}

	public String getIdPos() {
		return idPos;
	}

	public void setIdPos(String idPos) {
		this.idPos = idPos;
	}

    
}

