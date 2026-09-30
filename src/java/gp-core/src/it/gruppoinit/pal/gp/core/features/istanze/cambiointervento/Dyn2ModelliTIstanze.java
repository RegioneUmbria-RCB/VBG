package it.gruppoinit.pal.gp.core.features.istanze.cambiointervento;

import java.util.Set;

public class Dyn2ModelliTIstanze {

    private int codiceIstanza;
    private Set<Integer> idModelli;

    public Dyn2ModelliTIstanze(int codiceIstanza, Set<Integer> idModelli) {

	super();
	this.codiceIstanza = codiceIstanza;
	this.idModelli = idModelli;
    }

    public int getCodiceIstanza() {

	return codiceIstanza;
    }

    public Set<Integer> getIdModelli() {

	return idModelli;
    }
}
