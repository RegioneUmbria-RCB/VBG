package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione;

public class AllegatoComunicazione {

    private int codiceOggetto;

    public int getCodiceOggetto() {

	return codiceOggetto;
    }

    public AllegatoComunicazione() {

    }

    public AllegatoComunicazione(Integer codiceOggetto) {

	if (codiceOggetto == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il costruttore AllegatoComunicazione(Integer codiceOggetto) con codiceOggetto null");
	}
	this.codiceOggetto = codiceOggetto;
    }

    public void setCodiceOggetto(int codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }
}
