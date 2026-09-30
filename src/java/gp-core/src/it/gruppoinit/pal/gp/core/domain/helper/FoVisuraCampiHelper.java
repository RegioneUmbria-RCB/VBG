package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.FoVisuraCampi;
import it.gruppoinit.pal.gp.core.domain.FoVisuraContestiBase;

import java.util.List;

public class FoVisuraCampiHelper {

    private FoVisuraContestiBase contestoBase;
    private List<FoVisuraCampi> foVisuraCampis;

    public FoVisuraCampiHelper() {

	this.contestoBase = new FoVisuraContestiBase();
    }

    public FoVisuraContestiBase getContestoBase() {

	return contestoBase;
    }

    public void setContestoBase(FoVisuraContestiBase contestoBase) {

	this.contestoBase = contestoBase;
    }

    public List<FoVisuraCampi> getFoVisuraCampis() {

	return foVisuraCampis;
    }

    public void setFoVisuraCampis(List<FoVisuraCampi> foVisuraCampis) {

	this.foVisuraCampis = foVisuraCampis;
    }
}
