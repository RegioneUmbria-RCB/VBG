package it.gruppoinit.pal.gp.core.domain.web;

public class TipiMovSoggettoListItem {

    private int id;
    private String descrizione;

    public TipiMovSoggettoListItem(int id, String descrizione) {

	this.id = id;
	this.descrizione = descrizione;
    }

    public int getId() {

	return id;
    }

    public String getDescrizione() {

	return descrizione;
    }
}
