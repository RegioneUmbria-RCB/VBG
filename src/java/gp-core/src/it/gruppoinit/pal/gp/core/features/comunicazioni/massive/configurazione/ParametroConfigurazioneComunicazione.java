package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione;

public class ParametroConfigurazioneComunicazione {

    private String chiave;
    private String valore;

    public ParametroConfigurazioneComunicazione() {

    }

    public ParametroConfigurazioneComunicazione(String chiave, String valore) {

	this.chiave = chiave;
	this.valore = valore;
    }

    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
