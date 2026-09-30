package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;

public class AmministrazioniHelper {

    private Amministrazioni amministrazioni;
    // due campi utilizzati per gestiste i tempi di risposta di ogni singola amministrazioen
    private Short attesa;
    private Boolean calcoladainizioistanza;

    public Amministrazioni getAmministrazioni() {

	return amministrazioni;
    }

    public void setAmministrazioni(Amministrazioni amministrazioni) {

	this.amministrazioni = amministrazioni;
    }

    public Short getAttesa() {

	return attesa;
    }

    public void setAttesa(Short attesa) {

	this.attesa = attesa;
    }

    public Boolean getCalcoladainizioistanza() {

	return calcoladainizioistanza;
    }

    public void setCalcoladainizioistanza(Boolean calcoladainizioistanza) {

	this.calcoladainizioistanza = calcoladainizioistanza;
    }
}
