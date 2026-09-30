package it.gruppoinit.pal.gp.core.features.istanze.attivita;

import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.core.domain.Istanzestradario;

public class StradarioType {

    @XmlElement(name = "via")
    private String via;

    public StradarioType() {

	super();
    }

    public StradarioType(Istanzestradario istanzestradario) {

	super();
	this.via = istanzestradario.getDescrizioneEstesaTransient();
    }
}
