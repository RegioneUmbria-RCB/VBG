package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;

import java.util.List;

public class Dyn2ModellitHelper {

    private Dyn2Modellit scheda;
    private List<Dyn2CampiHelper> campiHelpers;

    public Dyn2ModellitHelper() {

	this.scheda = new Dyn2Modellit();
    }

    public Dyn2Modellit getScheda() {

	return scheda;
    }

    public void setScheda(Dyn2Modellit scheda) {

	this.scheda = scheda;
    }

    public List<Dyn2CampiHelper> getCampiHelpers() {

	return campiHelpers;
    }

    public void setCampiHelpers(List<Dyn2CampiHelper> campiHelpers) {

	this.campiHelpers = campiHelpers;
    }
}
