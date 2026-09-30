package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.GruppiEndoprocedimentiT;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.web.BaseCommand;

public class GruppiSmistamentoCommand extends BaseCommand {

    private GruppiEndoprocedimentiT gruppo1;
    private GruppiEndoprocedimentiT gruppo2;
    private GruppiEndoprocedimentiT gruppo3;
    private Alberoproc alberoproc;
    private Tipiprocedure proceduraScia;
    private Tipiprocedure proceduraOrdinario;

    public GruppiSmistamentoCommand() {

	inizializzaCampi();
    }

    public GruppiEndoprocedimentiT getGruppo1() {

	return gruppo1;
    }

    public void setGruppo1(GruppiEndoprocedimentiT gruppo1) {

	this.gruppo1 = gruppo1;
    }

    public GruppiEndoprocedimentiT getGruppo2() {

	return gruppo2;
    }

    public void setGruppo2(GruppiEndoprocedimentiT gruppo2) {

	this.gruppo2 = gruppo2;
    }

    public GruppiEndoprocedimentiT getGruppo3() {

	return gruppo3;
    }

    public void setGruppo3(GruppiEndoprocedimentiT gruppo3) {

	this.gruppo3 = gruppo3;
    }

    public Alberoproc getAlberoproc() {

	return alberoproc;
    }

    public void setAlberoproc(Alberoproc alberoproc) {

	this.alberoproc = alberoproc;
    }

    public Tipiprocedure getProceduraScia() {

	return proceduraScia;
    }

    public void setProceduraScia(Tipiprocedure proceduraScia) {

	this.proceduraScia = proceduraScia;
    }

    public Tipiprocedure getProceduraOrdinario() {

	return proceduraOrdinario;
    }

    public void setProceduraOrdinario(Tipiprocedure proceduraOrdinario) {

	this.proceduraOrdinario = proceduraOrdinario;
    }

    public void inizializzaCampi() {

	if (this.gruppo1 == null) {
	    this.gruppo1 = new GruppiEndoprocedimentiT();
	}
	if (this.gruppo2 == null) {
	    this.gruppo2 = new GruppiEndoprocedimentiT();
	}
	if (this.gruppo3 == null) {
	    this.gruppo3 = new GruppiEndoprocedimentiT();
	}
	if (this.alberoproc == null) {
	    this.alberoproc = new Alberoproc();
	}
	if (this.proceduraScia == null) {
	    this.proceduraScia = new Tipiprocedure();
	}
	if (this.proceduraOrdinario == null) {
	    this.proceduraOrdinario = new Tipiprocedure();
	}
    }
}
