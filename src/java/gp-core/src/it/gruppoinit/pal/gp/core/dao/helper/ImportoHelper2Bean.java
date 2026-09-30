package it.gruppoinit.pal.gp.core.dao.helper;

import it.gruppoinit.pal.gp.core.domain.MercatiCfgConti;

public class ImportoHelper2Bean {

    public ImportoHelper2Bean(MercatiCfgConti mercatiCfgConti) {

	this.mercatiCfgConti = mercatiCfgConti;
    }

    private MercatiCfgConti mercatiCfgConti;

    public MercatiCfgConti getMercatiCfgConti() {

	return mercatiCfgConti;
    }

    public void setMercatiCfgConti(MercatiCfgConti mercatiCfgConti) {

	this.mercatiCfgConti = mercatiCfgConti;
    }
}
