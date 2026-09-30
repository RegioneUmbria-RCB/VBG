package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo2;

public class StpTipologieendo2Helper {

    private StpTipologieEndo2 stpTipologieEndo2;
    private Azioni azioni;

    public StpTipologieendo2Helper() {

	this.stpTipologieEndo2 = new StpTipologieEndo2();
	this.azioni = new Azioni();
    }

    public StpTipologieEndo2 getStpTipologieEndo2() {

	return stpTipologieEndo2;
    }

    public void setStpTipologieEndo2(StpTipologieEndo2 stpTipologieEndo2) {

	this.stpTipologieEndo2 = stpTipologieEndo2;
    }

    public Azioni getAzioni() {

	return azioni;
    }

    public void setAzioni(Azioni azioni) {

	this.azioni = azioni;
    }
}
