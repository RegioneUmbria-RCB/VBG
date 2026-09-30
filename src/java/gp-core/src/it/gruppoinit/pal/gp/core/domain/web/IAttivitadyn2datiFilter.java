package it.gruppoinit.pal.gp.core.domain.web;

public class IAttivitadyn2datiFilter {

    private Integer fkIaId;
    private Integer fkD2cId;
    private Integer indice;
    private Integer indiceMolteplicita;

    public IAttivitadyn2datiFilter() {

	super();
    }

    public IAttivitadyn2datiFilter(Integer fkIaId, Integer fkD2cId, Integer indice, Integer indiceMolteplicita) {

	super();
	this.fkIaId = fkIaId;
	this.fkD2cId = fkD2cId;
	this.indice = indice;
	this.indiceMolteplicita = indiceMolteplicita;
    }

    public Integer getFkIaId() {

	return fkIaId;
    }

    public void setFkIaId(Integer fkIaId) {

	this.fkIaId = fkIaId;
    }

    public Integer getFkD2cId() {

	return fkD2cId;
    }

    public void setFkD2cId(Integer fkD2cId) {

	this.fkD2cId = fkD2cId;
    }

    public Integer getIndice() {

	return indice;
    }

    public void setIndice(Integer indice) {

	this.indice = indice;
    }

    public Integer getIndiceMolteplicita() {

	return indiceMolteplicita;
    }

    public void setIndiceMolteplicita(Integer indiceMolteplicita) {

	this.indiceMolteplicita = indiceMolteplicita;
    }
}
