package it.gruppoinit.pal.gp.pay.service.helper;

public enum CaricamentoMassivoStatiEnum {

    CARICATO("I"),
    PROCESSATO("P"),
    SCARTATO("S");

    private String valore;

    CaricamentoMassivoStatiEnum(String valore) {

	this.valore = valore;
    }

    public String getValore() {

	return valore;
    }
}
