package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Mappature;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.Dyn2ModellitHelper;

public class MappatureCommand extends BaseCommand {

    private Mappature entity;
    private String nometagpeople;
    private Software software;
    private Dyn2Modellit dyn2Modellit;
    private Dyn2ModellitHelper modellitHelper;

    public MappatureCommand() {

	super();
	this.entity = new Mappature();
	this.software = new Software();
	this.dyn2Modellit = new Dyn2Modellit();
	this.modellitHelper = new Dyn2ModellitHelper();
    }

    public Mappature getEntity() {

	return entity;
    }

    public void setEntity(Mappature entity) {

	this.entity = entity;
    }

    public String getNometagpeople() {

	return nometagpeople;
    }

    public void setNometagpeople(String nometagpeople) {

	this.nometagpeople = nometagpeople;
    }

    public Software getSoftware() {

	return software;
    }

    public void setSoftware(Software software) {

	this.software = software;
    }

    public Dyn2Modellit getDyn2Modellit() {

	return dyn2Modellit;
    }

    public void setDyn2Modellit(Dyn2Modellit dyn2Modellit) {

	this.dyn2Modellit = dyn2Modellit;
    }

    public Dyn2ModellitHelper getModellitHelper() {

	return modellitHelper;
    }

    public void setModellitHelper(Dyn2ModellitHelper modellitHelper) {

	this.modellitHelper = modellitHelper;
    }
}
