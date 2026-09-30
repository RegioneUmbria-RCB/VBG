package it.gruppoinit.pal.gp.core.features.attivita.snapshots.model;

import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;

public class AttivitaDyn2ModelliSnapshot {

    private Integer id;
    private String codiceScheda;
    private String descrizione;

    public AttivitaDyn2ModelliSnapshot(Dyn2Modellit dyn2Modellit) {

	super();
	if (dyn2Modellit == null || dyn2Modellit.getId() == null) {
	    return;
	}
	this.id = dyn2Modellit.getId().getCodice();
	this.codiceScheda = dyn2Modellit.getCodiceScheda();
	this.descrizione = dyn2Modellit.getDescrizione();
    }
}
