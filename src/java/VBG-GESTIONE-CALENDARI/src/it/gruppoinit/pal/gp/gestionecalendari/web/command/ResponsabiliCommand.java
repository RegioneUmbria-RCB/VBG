package it.gruppoinit.pal.gp.gestionecalendari.web.command;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

public class ResponsabiliCommand {

    private Responsabili responsabili;
    private Comuni comuni;

    public ResponsabiliCommand() {

	this.responsabili = new Responsabili();
	this.comuni = new Comuni();
    }

    public Responsabili getResponsabili() {

	return responsabili;
    }

    public void setResponsabili(Responsabili responsabili) {

	this.responsabili = responsabili;
    }

    public Comuni getComuni() {

	return comuni;
    }

    public void setComuni(Comuni comuni) {

	this.comuni = comuni;
    }
}
