package it.gruppoinit.pal.gp.pay.connector.easypa.ws.rest.modellazione;

import it.gov.digitpa.schemas._2011.pagamenti.v_6_2_0.CtRicevutaTelematica;

public class RispostaRicevutaTelematica {

    private CtRicevutaTelematica rt;
    private String rtAsString;

    public RispostaRicevutaTelematica(CtRicevutaTelematica rt, String rtAsString) {

	super();
	this.rt = rt;
	this.rtAsString = rtAsString;
    }

    public CtRicevutaTelematica getRt() {

	return rt;
    }

    public String getRtAsString() {

	return rtAsString;
    }
}
