package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.FoVisuraCampi;
import it.gruppoinit.pal.gp.core.domain.FoVisuraContestiBase;
import it.gruppoinit.pal.gp.core.domain.helper.FoVisuraCampiHelper;

import java.util.List;

public class FoVisuraCampiCommand extends BaseCommand {

    private FoVisuraCampi entity;
    private List<FoVisuraContestiBase> foVisuraContestiBases;
    private FoVisuraCampiHelper foVisuraCampiHelper;

    public FoVisuraCampiCommand() {

	super();
	this.entity = new FoVisuraCampi();
	this.foVisuraCampiHelper = new FoVisuraCampiHelper();
    }

    public FoVisuraCampi getEntity() {

	return entity;
    }

    public void setEntity(FoVisuraCampi entity) {

	this.entity = entity;
    }

    public List<FoVisuraContestiBase> getFoVisuraContestiBases() {

	return foVisuraContestiBases;
    }

    public void setFoVisuraContestiBases(List<FoVisuraContestiBase> foVisuraContestiBases) {

	this.foVisuraContestiBases = foVisuraContestiBases;
    }

    public FoVisuraCampiHelper getFoVisuraCampiHelper() {

	return foVisuraCampiHelper;
    }

    public void setFoVisuraCampiHelper(FoVisuraCampiHelper foVisuraCampiHelper) {

	this.foVisuraCampiHelper = foVisuraCampiHelper;
    }
}
