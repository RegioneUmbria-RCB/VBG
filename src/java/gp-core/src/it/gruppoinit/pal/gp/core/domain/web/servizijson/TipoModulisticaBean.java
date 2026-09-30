package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

public class TipoModulisticaBean {

    private String codice;
    private String nome;
    private List<ModulisticaBean> modulistica = new ArrayList<ModulisticaBean>();

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public List<ModulisticaBean> getModulistica() {

	return modulistica;
    }

    public void setModulistica(List<ModulisticaBean> modulistica) {

	this.modulistica = modulistica;
    }
}
