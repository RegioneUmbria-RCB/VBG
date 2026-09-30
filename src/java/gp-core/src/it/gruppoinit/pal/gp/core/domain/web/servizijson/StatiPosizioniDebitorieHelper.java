package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.Date;

public class StatiPosizioniDebitorieHelper {

    private Integer id;
    private String stato;
    private String descrizione;
    private Date dataEvento;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Date getDataEvento() {

	return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {

	this.dataEvento = dataEvento;
    }
}
