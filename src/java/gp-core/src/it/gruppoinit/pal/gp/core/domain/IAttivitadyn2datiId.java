package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IAttivitadyn2datiId implements Serializable {

    private static final long serialVersionUID = 789880084710669409L;
    private String idcomune;
    private Integer fkIaId;
    private Integer fkD2cId;
    private Integer indice;
    private Integer indiceMolteplicita;

    public IAttivitadyn2datiId(Integer fkIaId, Integer fkD2cId, Integer indice, Integer indiceMolteplicita) {

	this();
	this.fkIaId = fkIaId;
	this.fkD2cId = fkD2cId;
	this.indice = indice;
	this.indiceMolteplicita = indiceMolteplicita;
    }

    public IAttivitadyn2datiId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_IA_ID", nullable = false, precision = 6, scale = 0)
    public Integer getFkIaId() {

	return fkIaId;
    }

    public void setFkIaId(Integer fkIaId) {

	this.fkIaId = fkIaId;
    }

    @Column(name = "FK_D2C_ID", nullable = false, precision = 6, scale = 0)
    public Integer getFkD2cId() {

	return fkD2cId;
    }

    public void setFkD2cId(Integer fkD2cId) {

	this.fkD2cId = fkD2cId;
    }

    @Column(name = "INDICE", nullable = false, precision = 2, scale = 0)
    public Integer getIndice() {

	return indice;
    }

    public void setIndice(Integer indice) {

	this.indice = indice;
    }

    @Column(name = "INDICE_MOLTEPLICITA", nullable = false, precision = 2, scale = 0)
    public Integer getIndiceMolteplicita() {

	return indiceMolteplicita;
    }

    public void setIndiceMolteplicita(Integer indiceMolteplicita) {

	this.indiceMolteplicita = indiceMolteplicita;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((fkD2cId == null) ? 0 : fkD2cId.hashCode());
	result = prime * result + ((fkIaId == null) ? 0 : fkIaId.hashCode());
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	result = prime * result + ((indice == null) ? 0 : indice.hashCode());
	result = prime * result + ((indiceMolteplicita == null) ? 0 : indiceMolteplicita.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	IAttivitadyn2datiId other = (IAttivitadyn2datiId) obj;
	if (fkD2cId == null) {
	    if (other.fkD2cId != null) {
		return false;
	    }
	} else if (!fkD2cId.equals(other.fkD2cId)) {
	    return false;
	}
	if (fkIaId == null) {
	    if (other.fkIaId != null) {
		return false;
	    }
	} else if (!fkIaId.equals(other.fkIaId)) {
	    return false;
	}
	if (idcomune == null) {
	    if (other.idcomune != null) {
		return false;
	    }
	} else if (!idcomune.equals(other.idcomune)) {
	    return false;
	}
	if (indice == null) {
	    if (other.indice != null) {
		return false;
	    }
	} else if (!indice.equals(other.indice)) {
	    return false;
	}
	if (indiceMolteplicita == null) {
	    if (other.indiceMolteplicita != null) {
		return false;
	    }
	} else if (!indiceMolteplicita.equals(other.indiceMolteplicita)) {
	    return false;
	}
	return true;
    }
}
