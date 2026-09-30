package it.gruppoinit.pal.gp.core.domain.helper;

public class IstanzeAllegatiControlloHelper {

    private int allegatiRichiesti = 0;
    private int allegatiPresentati = 0;
    private int allegatiNonValidi = 0;
    private int allegatiValidi = 0;

    public IstanzeAllegatiControlloHelper() {

    }

    public int getAllegatiRichiesti() {

	return allegatiRichiesti;
    }

    public void setAllegatiRichiesti(int allegatiRichiesti) {

	this.allegatiRichiesti = allegatiRichiesti;
    }

    public int getAllegatiPresentati() {

	return allegatiPresentati;
    }

    public void setAllegatiPresentati(int allegatiPresentati) {

	this.allegatiPresentati = allegatiPresentati;
    }

    public int getAllegatiNonValidi() {

	return allegatiNonValidi;
    }

    public void setAllegatiNonValidi(int allegatiNonValidi) {

	this.allegatiNonValidi = allegatiNonValidi;
    }

    public int getAllegatiValidi() {

	return allegatiValidi;
    }

    public void setAllegatiValidi(int allegatiValidi) {

	this.allegatiValidi = allegatiValidi;
    }
}
