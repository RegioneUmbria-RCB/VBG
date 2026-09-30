package it.gruppoinit.pal.gp.core.dao.helper;

public enum GiorniSettimanaEnum {
    LUNEDI("Lunedì"), MARTEDI("Martedì"), MERCOLEDI("Mercoledì"), GIOVEDI("Giovedì"), VENERDI("Venerdì"), SABATO("Sabato"), DOMENICA("Domenica");

    private String valore;

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    private GiorniSettimanaEnum(String valore) {

	this.valore = valore;
    }
}
