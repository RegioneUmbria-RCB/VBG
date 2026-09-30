package it.gruppoinit.pal.gp.gestionecalendari.web.command;

import it.gruppoinit.pal.gp.core.domain.Comuniassociati;

public class EntiCommand {

    private Comuniassociati comuniassociati;

    public EntiCommand() {

	this.comuniassociati = new Comuniassociati();
    }

    public Comuniassociati getComuniassociati() {

	return comuniassociati;
    }

    public void setComuniassociati(Comuniassociati comuniassociati) {

	this.comuniassociati = comuniassociati;
    }
}
