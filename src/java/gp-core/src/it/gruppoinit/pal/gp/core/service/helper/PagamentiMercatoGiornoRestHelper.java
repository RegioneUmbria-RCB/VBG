package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.List;

public class PagamentiMercatoGiornoRestHelper {

    private Integer id;
    private String descrizione_giorno;
    private List<PagamentiMercatoPosizDebRestHelper> pagamenti;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getDescrizione_giorno() {

	return descrizione_giorno;
    }

    public void setDescrizione_giorno(String descrizione_giorno) {

	this.descrizione_giorno = descrizione_giorno;
    }

    public List<PagamentiMercatoPosizDebRestHelper> getPagamenti() {

	if (this.pagamenti == null) {
	    this.pagamenti = new ArrayList<PagamentiMercatoPosizDebRestHelper>();
	}
	return pagamenti;
    }

    public void setPagamenti(List<PagamentiMercatoPosizDebRestHelper> pagamenti) {

	this.pagamenti = pagamenti;
    }
}
