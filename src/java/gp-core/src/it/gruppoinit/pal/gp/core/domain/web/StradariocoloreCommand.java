package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Stradariocolore;

/**
 * 
 * @author gianpaolot
 * 
 */
public class StradariocoloreCommand extends BaseCommand {

    public StradariocoloreCommand() {

	super();
	this.entity = new Stradariocolore();
    }

    private Stradariocolore entity;

    public Stradariocolore getEntity() {

	return entity;
    }

    public void setEntity(Stradariocolore entity) {

	this.entity = entity;
    }
}
