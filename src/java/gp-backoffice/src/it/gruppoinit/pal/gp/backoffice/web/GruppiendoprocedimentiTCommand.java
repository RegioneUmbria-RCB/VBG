package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.GruppiEndoprocedimentiT;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.web.BaseCommand;

public class GruppiendoprocedimentiTCommand extends BaseCommand {

    private GruppiEndoprocedimentiT entity;
    private Inventarioprocedimenti inventarioprocedimenti;

    public GruppiendoprocedimentiTCommand() {

	this.entity = new GruppiEndoprocedimentiT();
	this.inventarioprocedimenti = new Inventarioprocedimenti();
    }

    public GruppiEndoprocedimentiT getEntity() {

	return entity;
    }

    public void setEntity(GruppiEndoprocedimentiT entity) {

	this.entity = entity;
    }

    public Inventarioprocedimenti getInventarioprocedimenti() {

	return inventarioprocedimenti;
    }

    public void setInventarioprocedimenti(Inventarioprocedimenti inventarioprocedimenti) {

	this.inventarioprocedimenti = inventarioprocedimenti;
    }
}
