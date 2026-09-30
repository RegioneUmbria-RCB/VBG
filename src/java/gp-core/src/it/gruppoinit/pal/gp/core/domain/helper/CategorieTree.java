package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.sigepro.cart.schema.rfcsuap.CategoriaAttivita;

import java.util.ArrayList;
import java.util.List;

public class CategorieTree {

    private CategoriaAttivita categoriaAttivita;
    private CategorieTree padre;
    private List<CategorieTree> figli = new ArrayList<CategorieTree>();

    public CategorieTree getPadre() {

	return padre;
    }

    public void setPadre(CategorieTree padre) {

	this.padre = padre;
    }

    public List<CategorieTree> getFigli() {

	return figli;
    }

    public void setFigli(List<CategorieTree> figli) {

	this.figli = figli;
    }

    public CategoriaAttivita getCategoriaAttivita() {

	return categoriaAttivita;
    }

    public void setCategoriaAttivita(CategoriaAttivita categoriaAttivita) {

	this.categoriaAttivita = categoriaAttivita;
    }
}
