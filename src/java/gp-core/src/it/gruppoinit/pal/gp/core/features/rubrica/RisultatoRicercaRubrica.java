package it.gruppoinit.pal.gp.core.features.rubrica;

public class RisultatoRicercaRubrica {

    private String descrizione;
    private String email;

    public RisultatoRicercaRubrica(String descrizione, String email) {

	this.descrizione = descrizione;
	this.email = email;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public String getEmail() {

	return email;
    }
}
