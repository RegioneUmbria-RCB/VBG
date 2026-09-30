package it.gruppoinit.pal.gp.core.features.anagrafetributaria.model;

import java.util.Date;

public class ListaEsitiTracciatoBean {

    private Integer id;
    private String descrizione;
    private Date data;
    private String responsabile;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getResponsabile() {

	return responsabile;
    }

    public void setResponsabile(String responsabile) {

	this.responsabile = responsabile;
    }
}
