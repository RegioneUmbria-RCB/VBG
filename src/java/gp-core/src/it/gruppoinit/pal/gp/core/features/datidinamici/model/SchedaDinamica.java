package it.gruppoinit.pal.gp.core.features.datidinamici.model;

import java.util.List;

public class SchedaDinamica {

    private Integer id;
    private String codiceScheda;
    private String descrizione;
    private List<CampoDinamicoScheda> campi;

    public SchedaDinamica(Integer id, String codiceScheda, String descrizione, List<CampoDinamicoScheda> campi) {

	super();
	this.id = id;
	this.codiceScheda = codiceScheda;
	this.descrizione = descrizione;
	this.campi = campi;
    }

    public Integer getId() {

	return id;
    }

    public String getCodiceScheda() {

	return codiceScheda;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public List<CampoDinamicoScheda> getCampi() {

	return campi;
    }
}
