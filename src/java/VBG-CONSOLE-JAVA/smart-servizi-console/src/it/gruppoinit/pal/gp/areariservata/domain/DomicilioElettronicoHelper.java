package it.gruppoinit.pal.gp.areariservata.domain;

public class DomicilioElettronicoHelper {

    private String email;
    private String estremiSoggetto;
    private boolean pec;

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public String getEstremiSoggetto() {

	return estremiSoggetto;
    }

    public void setEstremiSoggetto(String estremiSoggetto) {

	this.estremiSoggetto = estremiSoggetto;
    }

    public boolean isPec() {

	return pec;
    }

    public void setPec(boolean pec) {

	this.pec = pec;
    }

    @Override
    public String toString() {

	return estremiSoggetto;
    }
}
