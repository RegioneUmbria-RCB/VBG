package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.List;

public class PagamentiMercatoRestHelper {

    private Integer id;
    private String descrizione_mercato;
    private List<PagamentiMercatoGiornoRestHelper> giorno;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getDescrizione_mercato() {

	return descrizione_mercato;
    }

    public void setDescrizione_mercato(String descrizione_mercato) {

	this.descrizione_mercato = descrizione_mercato;
    }

    public List<PagamentiMercatoGiornoRestHelper> getGiorno() {

	if (this.giorno == null) {
	    this.giorno = new ArrayList<PagamentiMercatoGiornoRestHelper>();
	}
	return giorno;
    }

    public void setGiorno(List<PagamentiMercatoGiornoRestHelper> giorno) {

	this.giorno = giorno;
    }
}
