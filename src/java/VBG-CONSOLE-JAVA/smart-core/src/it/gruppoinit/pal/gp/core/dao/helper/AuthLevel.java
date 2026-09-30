package it.gruppoinit.pal.gp.core.dao.helper;

public enum AuthLevel {
    ANONIMO("Anonimo", 0), UTENTE_NON_IDENTIFICATO("Utente non identificato", 1), UTENTE_IDENTIFICATO("Utente identificato", 2);

    private String descrizione;
    private int valore;

    public String getDescrizione() {

	return descrizione;
    }

    public int getValore() {

	return valore;
    }

    private AuthLevel(String descrizione, int valore) {

	this.descrizione = descrizione;
	this.valore = valore;
    }
}
