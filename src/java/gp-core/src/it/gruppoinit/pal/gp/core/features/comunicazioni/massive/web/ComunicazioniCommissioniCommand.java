package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioneModel;

public class ComunicazioniCommissioniCommand extends ComunicazioniBaseCommand {

    private CommissioneModel commissione;

    public ComunicazioniCommissioniCommand() {

	super();
	this.commissione = new CommissioneModel();
    }

    public CommissioneModel getCommissione() {

	return commissione;
    }

    public void setCommissione(CommissioneModel commissione) {

	this.commissione = commissione;
    }
}
