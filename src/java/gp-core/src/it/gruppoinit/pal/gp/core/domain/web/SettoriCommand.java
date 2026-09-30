package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Settori;

public class SettoriCommand extends BaseCommand {

    public SettoriCommand() {

	super();
	this.entity = new Settori();
    }

    private Settori entity;

    public Settori getEntity() {

	return entity;
    }

    public void setEntity(Settori entity) {

	this.entity = entity;
    }
}
