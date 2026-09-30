package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionir;
import it.gruppoinit.pal.gp.core.domain.CcCausaliriduzionit;

public class CcCausaliriduzionitCommand extends BaseCommand {

    private CcCausaliriduzionit entity;
    private CcCausaliriduzionir ccCausaliriduzionir;

    public CcCausaliriduzionitCommand() {

	super();
	this.entity = new CcCausaliriduzionit();
	this.ccCausaliriduzionir = new CcCausaliriduzionir();
    }

    public CcCausaliriduzionit getEntity() {

	return entity;
    }

    public void setEntiry(CcCausaliriduzionit entity) {

	this.entity = entity;
    }

    public CcCausaliriduzionir getCcCausaliriduzionir() {

	return ccCausaliriduzionir;
    }

    public void setCcCausaliriduzionir(CcCausaliriduzionir ccCausaliriduzionir) {

	this.ccCausaliriduzionir = ccCausaliriduzionir;
    }
}
