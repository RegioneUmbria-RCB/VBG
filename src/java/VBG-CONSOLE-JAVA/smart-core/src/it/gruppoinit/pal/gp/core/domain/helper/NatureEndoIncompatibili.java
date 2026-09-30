package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.Naturaendobase;

public class NatureEndoIncompatibili {

    private Naturaendobase naturaEndo;
    private Naturaendobase naturaIncompatibile;
    private String endoIncompatibile;

    public Naturaendobase getNaturaEndo() {

	return naturaEndo;
    }

    public void setNaturaEndo(Naturaendobase naturaEndo) {

	this.naturaEndo = naturaEndo;
    }

    public Naturaendobase getNaturaIncompatibile() {

	return naturaIncompatibile;
    }

    public void setNaturaIncompatibile(Naturaendobase naturaIncompatibile) {

	this.naturaIncompatibile = naturaIncompatibile;
    }

    public String getEndoIncompatibile() {

	return endoIncompatibile;
    }

    public void setEndoIncompatibile(String endoIncompatibile) {

	this.endoIncompatibile = endoIncompatibile;
    }
}
