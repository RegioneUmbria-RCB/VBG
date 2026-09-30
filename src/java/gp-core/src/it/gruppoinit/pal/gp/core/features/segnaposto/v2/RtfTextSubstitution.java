package it.gruppoinit.pal.gp.core.features.segnaposto.v2;

public class RtfTextSubstitution implements IRtfSubstitution {

    private String valore;

    public RtfTextSubstitution(String valore) {

	this.valore = valore;
    }

    @Override
    public String getValore() {

	return this.valore;
    }
}
