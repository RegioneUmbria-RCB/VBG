package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

public class SoftwareBean {

    private String codice;
    private String tipo;
    private List<TipoModulisticaBean> categorie = new ArrayList<TipoModulisticaBean>();

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getTipo() {

	return tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }

    public List<TipoModulisticaBean> getCategorie() {

	return categorie;
    }

    public void setCategorie(List<TipoModulisticaBean> categorie) {

	this.categorie = categorie;
    }
}
