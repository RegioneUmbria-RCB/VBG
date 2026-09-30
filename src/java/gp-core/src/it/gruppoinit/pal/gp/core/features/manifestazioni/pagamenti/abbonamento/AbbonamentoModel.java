package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import it.gruppoinit.pal.gp.core.domain.Borsellino;

public class AbbonamentoModel {

    private Integer id;
    private String descrizione;

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

    public static AbbonamentoModel fromBorsellino(Borsellino borsellino) {

	if (borsellino == null || borsellino.getId() == null) {
	    return null;
	}
	AbbonamentoModel model = new AbbonamentoModel();
	model.setId(borsellino.getId().getCodice());
	model.setDescrizione(borsellino.getDescrizione());
	return model;
    }
}
