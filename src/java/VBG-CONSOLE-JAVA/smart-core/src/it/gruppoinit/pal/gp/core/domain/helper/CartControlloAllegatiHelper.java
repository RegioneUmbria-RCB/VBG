package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.ArrayList;
import java.util.List;

public class CartControlloAllegatiHelper {

    private boolean limiteSuperato = false;
    private String limite = "40Mb";

    public String getLimite() {

	return limite;
    }

    public void setLimite(String limite) {

	this.limite = limite;
    }

    private List<String> listaAllegati = new ArrayList<String>();

    public boolean isLimiteSuperato() {

	return limiteSuperato;
    }

    public void setLimiteSuperato(boolean limiteSuperato) {

	this.limiteSuperato = limiteSuperato;
    }

    public List<String> getListaAllegati() {

	return listaAllegati;
    }

    public void setListaAllegati(List<String> listaAllegati) {

	this.listaAllegati = listaAllegati;
    }
}
