package it.gruppoinit.pal.gp.core.features.segnaposto.v2;

import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.RtfConstants;

public class RtfMultipleTextSubstitution implements IRtfSubstitution {

    private String[] valori;

    public RtfMultipleTextSubstitution(String[] valori) {

	this.valori = valori;
	if (this.valori == null) {
	    this.valori = new String[0];
	}
    }

    @Override
    public String getValore() {

	if (this.valori.length > 1) {
	    StringBuilder sb = new StringBuilder();
	    for (String valore : valori) {
		sb.append(valore).append(RtfConstants.RTF_CRLF);
	    }
	    return sb.toString();
	}
	String valoreSingolo = this.valori.length == 0 ? "" : this.valori[0];
	return new RtfTextSubstitution(valoreSingolo).getValore();
    }
}
