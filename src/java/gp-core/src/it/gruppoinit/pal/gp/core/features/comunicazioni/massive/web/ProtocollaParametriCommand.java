package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Mailtipo;

public class ProtocollaParametriCommand {

    private boolean protocolla;
    private List<IParametriProtocolloPerEnteHelper> parametriPerEnte;
    private Mailtipo mailtipo;

    public ProtocollaParametriCommand() {

	this.mailtipo = new Mailtipo();
    }

    public boolean isProtocolla() {

	return protocolla;
    }

    public void setProtocolla(boolean protocolla) {

	this.protocolla = protocolla;
    }

    public Mailtipo getMailtipo() {

	return mailtipo;
    }

    public void setMailtipo(Mailtipo mailtipo) {

	this.mailtipo = mailtipo;
    }

    public List<IParametriProtocolloPerEnteHelper> getParametriPerEnte() {

	if (this.parametriPerEnte == null) {
	    this.parametriPerEnte = new ArrayList<IParametriProtocolloPerEnteHelper>();
	}
	return parametriPerEnte;
    }

    public void setParametriPerEnte(List<IParametriProtocolloPerEnteHelper> parametriPerEnte) {

	this.parametriPerEnte = parametriPerEnte;
    }
}
