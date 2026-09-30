package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.List;

public class TipologiaEndoBean {

    private Integer id;
    private String nome;
    private int ordine;
    private List<EndoprocedimentoSimpleBean> endoprocedimenti;
    private boolean intervento;

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

    public List<EndoprocedimentoSimpleBean> getEndoprocedimenti() {

	return endoprocedimenti;
    }

    public void setEndoprocedimenti(List<EndoprocedimentoSimpleBean> endoprocedimenti) {

	this.endoprocedimenti = endoprocedimenti;
    }

    public int getOrdine() {

	return ordine;
    }

    public void setOrdine(int ordine) {

	this.ordine = ordine;
    }

    public boolean isIntervento() {

	return intervento;
    }

    public void setIntervento(boolean intervento) {

	this.intervento = intervento;
    }
}
