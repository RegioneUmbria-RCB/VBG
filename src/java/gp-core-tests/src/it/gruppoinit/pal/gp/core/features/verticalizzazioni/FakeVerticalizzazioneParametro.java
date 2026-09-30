package it.gruppoinit.pal.gp.core.features.verticalizzazioni;

public class FakeVerticalizzazioneParametro {

    private String modulo;
    private String parametro;
    private String codiceComune;
    private String software;
    private String valore;

    public FakeVerticalizzazioneParametro(String modulo, String parametro, String codiceComune, String software, String valore) {

	this.modulo = modulo;
	this.parametro = parametro;
	this.codiceComune = codiceComune;
	this.software = software;
	this.valore = valore;
    }

    public String getModulo() {

	return modulo;
    }

    public String getParametro() {

	return parametro;
    }

    public String getCodiceComune() {

	return codiceComune;
    }

    public String getSoftware() {

	return software;
    }

    public String getValore() {

	return valore;
    }
}
