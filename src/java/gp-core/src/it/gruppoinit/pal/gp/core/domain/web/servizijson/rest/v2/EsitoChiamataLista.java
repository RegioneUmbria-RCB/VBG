package it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2;

import java.util.ArrayList;
import java.util.List;

public class EsitoChiamataLista<T> {

    private long numero_record;
    private List<T> lista;

    public long getNumero_record() {

	return numero_record;
    }

    public void setNumero_record(long numero_record) {

	this.numero_record = numero_record;
    }

    public List<T> getLista() {

	if (this.lista == null) {
	    this.lista = new ArrayList<T>(0);
	}
	return lista;
    }

    public void setLista(List<T> lista) {

	this.lista = lista;
    }
}
