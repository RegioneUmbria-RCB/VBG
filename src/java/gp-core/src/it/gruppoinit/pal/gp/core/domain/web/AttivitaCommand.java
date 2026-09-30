package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Attivita;

public class AttivitaCommand extends BaseCommand {

    public AttivitaCommand() {

	super();
	this.entity = new Attivita();
    }

    private Attivita entity;

    public Attivita getEntity() {

	return entity;
    }

    public void setEntity(Attivita entity) {

	this.entity = entity;
    }
}
