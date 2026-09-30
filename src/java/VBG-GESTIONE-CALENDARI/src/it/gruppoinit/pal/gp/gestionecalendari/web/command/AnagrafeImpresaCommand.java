package it.gruppoinit.pal.gp.gestionecalendari.web.command;

import it.gruppoinit.pal.gp.core.domain.AnagrafeImpresa;

public class AnagrafeImpresaCommand {

    private AnagrafeImpresa entity;

    public AnagrafeImpresaCommand() {

	this.entity = new AnagrafeImpresa();
    }

    public AnagrafeImpresa getEntity() {

	return entity;
    }

    public void setEntity(AnagrafeImpresa entity) {

	this.entity = entity;
    }
}
