package it.gruppoinit.pal.gp.core.features.comunicazioni.massive;

import java.io.Serializable;

public class MassiveDettaglioAggiornaMail extends MassiveDettaglioDaAggiornare implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1941242310621970423L;
    private String email;
    private String pec;

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public String getPec() {

	return pec;
    }

    public void setPec(String pec) {

	this.pec = pec;
    }
}
