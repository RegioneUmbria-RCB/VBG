package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.StringUtils;

public class Firmatario {

    @XmlElement(name = "responsabile")
    private String responsabile;
    @XmlElement(name = "firmato")
    private boolean firmato;

    public String getResponsabile() {

	return responsabile;
    }

    public void setResponsabile(String responsabile) {

	this.responsabile = responsabile;
    }

    public boolean isFirmato() {

	return firmato;
    }

    public void setFirmato(boolean firmato) {

	this.firmato = firmato;
    }

    public static Firmatario FromFirmatari(String firmatario, boolean firmaCompletataConSuccesso) {

	if (StringUtils.isBlank(firmatario)) {
	    throw new IllegalArgumentException("Impossibile richiamare Firmatario.FromFirmatari senza passare il parametro firmatario valido");
	}
	Firmatario retval = new Firmatario();
	retval.setResponsabile(firmatario);
	retval.setFirmato(firmaCompletataConSuccesso);
	return retval;
    }
}
