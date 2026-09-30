package it.gruppoinit.nlapec.daticert;

public class PecDaticert {

    private String tipo;
    private String errore;
    private PecDatiCertIntestazione intestazione;
    private PecDatiCertDati dati;
    private int returnValue;
    public static final int VALID = 1;
    public static final int INVALID = 0;

    public PecDaticert() {

	intestazione = new PecDatiCertIntestazione();
	dati = new PecDatiCertDati();
    }

    public String getTipo() {

	return tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }

    public void setReturnValue(int returnValue) {

	this.returnValue = returnValue;
    }

    public int getReturnValue() {

	return returnValue;
    }

    public PecDatiCertIntestazione getIntestazione() {

	return intestazione;
    }

    public PecDatiCertDati getDati() {

	return dati;
    }
}
