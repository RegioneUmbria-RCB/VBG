package it.gruppoinit.pal.gp.core.service.helper;

public class TempirispostaValoriHelper {

    private int codiceprocedura;
    private int codiceamministrazione;
    private Short attesa;
    private boolean calcoladainizioistanza;

    public TempirispostaValoriHelper(int codiceprocedura, int codiceamministrazione, Short attesa, boolean calcoladainizioistanza) {

	super();
	this.codiceprocedura = codiceprocedura;
	this.codiceamministrazione = codiceamministrazione;
	this.attesa = attesa;
	this.calcoladainizioistanza = calcoladainizioistanza;
    }

    public int getCodiceprocedura() {

	return codiceprocedura;
    }

    public int getCodiceamministrazione() {

	return codiceamministrazione;
    }

    public Short getAttesa() {

	return attesa;
    }

    public boolean isCalcoladainizioistanza() {

	return calcoladainizioistanza;
    }
}
