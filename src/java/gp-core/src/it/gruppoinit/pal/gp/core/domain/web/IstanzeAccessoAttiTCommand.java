package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiAnagrafe;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiT;

public class IstanzeAccessoAttiTCommand extends BaseCommand {

    private IstanzeAccessoAttiT entity;
    private IstanzeAccessoAttiAnagrafe attianagrafe;
    private Anagrafe anagrafe;

    public IstanzeAccessoAttiTCommand() {

	super();
	this.entity = new IstanzeAccessoAttiT();
	this.attianagrafe = new IstanzeAccessoAttiAnagrafe();
	this.setAnagrafe(new Anagrafe());
    }

    public IstanzeAccessoAttiT getEntity() {

	return entity;
    }

    public void setEntity(IstanzeAccessoAttiT entity) {

	this.entity = entity;
    }

    public IstanzeAccessoAttiAnagrafe getAttianagrafe() {

	return attianagrafe;
    }

    public void setAttianagrafe(IstanzeAccessoAttiAnagrafe attianagrafe) {

	this.attianagrafe = attianagrafe;
    }

    public Anagrafe getAnagrafe() {

	return anagrafe;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }
}
