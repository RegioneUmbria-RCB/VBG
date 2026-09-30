package it.gruppoinit.pal.gp.areariservata.web.rest;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;

public class ContoResponseType {

    private Integer id;
    private String conto;
    private Integer iva;
    private String mappaturaNodoPag;

    public ContoResponseType(Conti conto, Tipicausalioneri tco) {

	if (conto == null || conto.getId() == null || tco == null || StringUtils.isBlank(conto.getMappaturanodopag())) {
	    return;
	}
	this.id = conto.getId().getCodice();
	this.conto = conto.getDescrizione();
	this.iva = conto.getIva();
	this.mappaturaNodoPag = conto.getMappaturanodopag();
    }

    public Integer getId() {

	return id;
    }

    public String getConto() {

	return conto;
    }

    public Integer getIva() {

	return iva;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public void setConto(String conto) {

	this.conto = conto;
    }

    public void setIva(Integer iva) {

	this.iva = iva;
    }

    public String getMappaturaNodoPag() {

	return mappaturaNodoPag;
    }

    public void setMappaturaNodoPag(String mappaturaNodoPag) {

	this.mappaturaNodoPag = mappaturaNodoPag;
    }
}
