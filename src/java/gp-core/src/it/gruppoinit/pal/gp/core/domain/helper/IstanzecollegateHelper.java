package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.VwIstanzecollegate;

import java.util.ArrayList;
import java.util.List;

public class IstanzecollegateHelper {

    private Istanze istanza;
    private List<VwIstanzecollegate> istanzecollegates = new ArrayList<VwIstanzecollegate>();
    // lista di istanze che sono state collegate all'istanza in esame
    private List<Istanze> listaIstanzePrecedenti = new ArrayList<Istanze>();
    // Lista delle istanze a cui l'istanza in esame è stata collegata
    private List<Istanze> listaIstanzeSuccessive = new ArrayList<Istanze>();

    public Istanze getIstanza() {

	return istanza;
    }

    public void setIstanza(Istanze istanza) {

	this.istanza = istanza;
    }

    public List<VwIstanzecollegate> getIstanzecollegates() {

	return istanzecollegates;
    }

    public void setIstanzecollegates(List<VwIstanzecollegate> istanzecollegates) {

	this.istanzecollegates = istanzecollegates;
    }

    public List<Istanze> getListaIstanzePrecedenti() {

	return listaIstanzePrecedenti;
    }

    public void setListaIstanzePrecedenti(List<Istanze> listaIstanzePrecedenti) {

	this.listaIstanzePrecedenti = listaIstanzePrecedenti;
    }

    public List<Istanze> getListaIstanzeSuccessive() {

	return listaIstanzeSuccessive;
    }

    public void setListaIstanzeSuccessive(List<Istanze> listaIstanzeSuccessive) {

	this.listaIstanzeSuccessive = listaIstanzeSuccessive;
    }
}
