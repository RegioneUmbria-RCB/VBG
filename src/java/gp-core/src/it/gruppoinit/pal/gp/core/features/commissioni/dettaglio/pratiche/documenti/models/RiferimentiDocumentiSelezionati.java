package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models;

import java.util.ArrayList;
import java.util.List;

public class RiferimentiDocumentiSelezionati {

    private List<Integer> documentiIstanza = new ArrayList<Integer>();
    private List<Integer> documentiEndo = new ArrayList<Integer>();
    private List<Integer> documentiMovimenti = new ArrayList<Integer>();

    public List<Integer> getDocumentiIstanza() {

	return documentiIstanza;
    }

    public List<Integer> getDocumentiEndo() {

	return documentiEndo;
    }

    public List<Integer> getDocumentiMovimenti() {

	return documentiMovimenti;
    }

    public void setDocumentiMovimenti(List<Integer> listaId) {

	this.documentiMovimenti.addAll(listaId);
    }

    public void setDocumentiEndo(List<Integer> listaId) {

	this.documentiEndo.addAll(listaId);
    }

    public void setDocumentiIstanza(List<Integer> listaId) {

	this.documentiIstanza.addAll(listaId);
    }
}
