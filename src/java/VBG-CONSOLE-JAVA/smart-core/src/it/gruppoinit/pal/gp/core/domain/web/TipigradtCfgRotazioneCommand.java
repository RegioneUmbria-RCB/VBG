package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.TipigradtCfgRotazione;

public class TipigradtCfgRotazioneCommand extends BaseCommand {

    private TipigradtCfgRotazione entity;
    private Dyn2Modellit dyn2Modellit;
//    private Dyn2Modellit dyn2ModellitPosteggio;

    public TipigradtCfgRotazioneCommand() {

	this.entity = new TipigradtCfgRotazione();
	this.dyn2Modellit = new Dyn2Modellit();
//	this.dyn2ModellitPosteggio = new Dyn2Modellit();
    }

    public TipigradtCfgRotazione getEntity() {

	return entity;
    }

    public void setEntity(TipigradtCfgRotazione entity) {

	this.entity = entity;
    }

    public Dyn2Modellit getDyn2Modellit() {

	return dyn2Modellit;
    }

    public void setDyn2Modellit(Dyn2Modellit dyn2Modellit) {

	this.dyn2Modellit = dyn2Modellit;
    }

    //    public Dyn2Modellit getDyn2ModellitPosteggio() {
    //
    //	return dyn2ModellitPosteggio;
    //    }
    //
    //    public void setDyn2ModellitPosteggio(Dyn2Modellit dyn2ModellitPosteggio) {
    //
    //	this.dyn2ModellitPosteggio = dyn2ModellitPosteggio;
    //    }
}
