package it.gruppoinit.pal.gp.areariservata.domain;

import it.init.sigepro.rte.types.EstremiAttoType;

public class EstremiAttoTypeHelper {

    private EstremiAttoType estremiAttoType;
    private ProcedimentoHelper procedimentoHelper;

    public EstremiAttoTypeHelper(EstremiAttoType estremiAttoType, ProcedimentoHelper procedimentoHelper) {

	this.estremiAttoType = estremiAttoType;
	this.procedimentoHelper = procedimentoHelper;
    }

    public EstremiAttoType getEstremiAttoType() {

	return estremiAttoType;
    }

    public void setEstremiAttoType(EstremiAttoType estremiAttoType) {

	this.estremiAttoType = estremiAttoType;
    }

    public ProcedimentoHelper getProcedimentoHelper() {

	return procedimentoHelper;
    }

    public void setProcedimentoHelper(ProcedimentoHelper procedimentoHelper) {

	this.procedimentoHelper = procedimentoHelper;
    }
}
