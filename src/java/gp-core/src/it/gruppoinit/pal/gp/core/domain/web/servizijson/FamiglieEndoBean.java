package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.List;

public class FamiglieEndoBean {

    private Integer id;
    private String famiglia;
    private List<TipologiaEndoBean> tipologie;
    private int ordine;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getFamiglia() {

	return famiglia;
    }

    public void setFamiglia(String famiglia) {

	this.famiglia = famiglia;
    }

    public List<TipologiaEndoBean> getTipologie() {

	return tipologie;
    }

    public void setTipologie(List<TipologiaEndoBean> tipologie) {

	this.tipologie = tipologie;
    }

    public int getOrdine() {

	return ordine;
    }

    public void setOrdine(int ordine) {

	this.ordine = ordine;
    }
}
