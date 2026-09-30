package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.TipigraduatorietEsprArt;

public class TipigraduatorietEsprArtCommand {

    private TipigraduatorietEsprArt entity;
    private Dyn2Modellit dyn2Modellit;

    //    private Dyn2Modellit dyn2ModellitDa;
    //    private Dyn2Modellit dyn2ModellitPosteggio;
    public TipigraduatorietEsprArtCommand() {

	this.entity = new TipigraduatorietEsprArt();
	this.dyn2Modellit = new Dyn2Modellit();
	//	this.dyn2ModellitDa = new Dyn2Modellit();
	//	this.dyn2ModellitPosteggio = new Dyn2Modellit();
    }

    public Dyn2Modellit getDyn2Modellit() {

	return dyn2Modellit;
    }

    public void setDyn2Modellit(Dyn2Modellit dyn2ModellitA) {

	this.dyn2Modellit = dyn2ModellitA;
    }

    //    public Dyn2Modellit getDyn2ModellitDa() {
    //
    //	return dyn2ModellitDa;
    //    }
    //
    //    public void setDyn2ModellitDa(Dyn2Modellit dyn2ModellitDa) {
    //
    //	this.dyn2ModellitDa = dyn2ModellitDa;
    //    }
    //
    //    public Dyn2Modellit getDyn2ModellitPosteggio() {
    //
    //	return dyn2ModellitPosteggio;
    //    }
    //
    //    public void setDyn2ModellitPosteggio(Dyn2Modellit dyn2ModellitPosteggio) {
    //
    //	this.dyn2ModellitPosteggio = dyn2ModellitPosteggio;
    //    }
    public TipigraduatorietEsprArt getEntity() {

	return entity;
    }

    public void setEntity(TipigraduatorietEsprArt entity) {

	this.entity = entity;
    }
}
