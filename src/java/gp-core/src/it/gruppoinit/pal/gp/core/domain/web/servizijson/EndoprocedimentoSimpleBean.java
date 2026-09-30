package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

public class EndoprocedimentoSimpleBean {

    private Integer id;
    private String nome;
    private int ordine;
    private Boolean principale;
    private List<EndoprocedimentoSimpleBean> procedimentiCollegati = new ArrayList<EndoprocedimentoSimpleBean>();

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public int getOrdine() {

	return ordine;
    }

    public void setOrdine(int ordine) {

	this.ordine = ordine;
    }

    public Boolean getPrincipale() {

	return principale;
    }

    public void setPrincipale(Boolean principale) {

	this.principale = principale;
    }

    public List<EndoprocedimentoSimpleBean> getProcedimentiCollegati() {

	return procedimentiCollegati;
    }

    public void setProcedimentiCollegati(List<EndoprocedimentoSimpleBean> procedimentiCollegati) {

	this.procedimentiCollegati = procedimentiCollegati;
    }
}
