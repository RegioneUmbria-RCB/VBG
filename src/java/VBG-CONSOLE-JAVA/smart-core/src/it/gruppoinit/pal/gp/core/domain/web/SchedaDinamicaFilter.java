package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SchedaDinamicaFilter implements Serializable {

    private static final long serialVersionUID = 7802044472053224725L;
    private Dyn2Modellit scheda;
    private List<SchedaDinamicaRigheFilter> righe = new ArrayList<SchedaDinamicaRigheFilter>();
    private List<Dyn2Campi> listaCampiModello = new ArrayList<Dyn2Campi>();

    public SchedaDinamicaFilter() {

	this.scheda = new Dyn2Modellit();
    }

    public Dyn2Modellit getScheda() {

	return scheda;
    }

    public void setScheda(Dyn2Modellit scheda) {

	this.scheda = scheda;
    }

    public List<SchedaDinamicaRigheFilter> getRighe() {

	return righe;
    }

    public void setRighe(List<SchedaDinamicaRigheFilter> righe) {

	this.righe = righe;
    }

    public List<Dyn2Campi> getListaCampiModello() {

	return listaCampiModello;
    }

    public void setListaCampiModello(List<Dyn2Campi> listaCampiModello) {

	this.listaCampiModello = listaCampiModello;
    }
}
