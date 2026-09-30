package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Naturaendo;

public class NatureEndoIncompatibili {

    private Naturaendo naturaEndo;
    private Naturaendo naturaIncompatibile;
    private String endoIncompatibile;

    public Naturaendo getNaturaEndo() {
	return naturaEndo;
    }

    public void setNaturaEndo(Naturaendo naturaEndo) {
	this.naturaEndo = naturaEndo;
    }

    public Naturaendo getNaturaIncompatibile() {
	return naturaIncompatibile;
    }

    public void setNaturaIncompatibile(Naturaendo naturaIncompatibile) {
	this.naturaIncompatibile = naturaIncompatibile;
    }

    public String getEndoIncompatibile() {
	return endoIncompatibile;
    }

    public void setEndoIncompatibile(String endoIncompatibile) {
	this.endoIncompatibile = endoIncompatibile;
    }
}
