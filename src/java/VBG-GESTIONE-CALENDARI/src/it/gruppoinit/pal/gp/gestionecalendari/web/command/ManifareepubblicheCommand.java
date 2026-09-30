package it.gruppoinit.pal.gp.gestionecalendari.web.command;

import it.gruppoinit.pal.gp.core.domain.ManifAreePubbliche;

public class ManifareepubblicheCommand {

    private ManifAreePubbliche entity;

    public ManifareepubblicheCommand() {

	this.entity = new ManifAreePubbliche();
    }

    public ManifAreePubbliche getEntity() {

	return entity;
    }

    public void setEntity(ManifAreePubbliche entity) {

	this.entity = entity;
    }
}
