package it.gruppoinit.pal.gp.areariservata.web.rest;

import it.gruppoinit.pal.gp.core.domain.Statiistanza;

public class StatiistanzaType {

    private String id;
    private String stato;
    private String software;

    public StatiistanzaType() {

	//costruttore vuoto
    }

    public StatiistanzaType(Statiistanza statiistanza) {

	if (statiistanza == null) {
	    return;
	}
	this.id = statiistanza.getId().getCodicestato();
	this.stato = statiistanza.getStato();
	this.software = statiistanza.getId().getSoftware();
    }

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }
}
