package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IAttivitadyn2modellitId implements Serializable {

    private static final long serialVersionUID = -2766433092082717435L;
    private String idcomune;
    private Integer fkD2mtId;
    private Integer fkIaId;

    public IAttivitadyn2modellitId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public IAttivitadyn2modellitId(Integer fkD2mtId, Integer fkIaId) {

	this();
	this.fkD2mtId = fkD2mtId;
	this.fkIaId = fkIaId;
    }

    public IAttivitadyn2modellitId(String idcomune, Integer fkD2mtId, Integer fkIaId) {

	this();
	this.idcomune = idcomune;
	this.fkD2mtId = fkD2mtId;
	this.fkIaId = fkIaId;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_D2MT_ID", nullable = false, precision = 6, scale = 0)
    public Integer getFkD2mtId() {

	return fkD2mtId;
    }

    public void setFkD2mtId(Integer fkD2mtId) {

	this.fkD2mtId = fkD2mtId;
    }

    @Column(name = "FK_IA_ID", nullable = false, precision = 6, scale = 0)
    public Integer getFkIaId() {

	return fkIaId;
    }

    public void setFkIaId(Integer fkIaId) {

	this.fkIaId = fkIaId;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((fkD2mtId == null) ? 0 : fkD2mtId.hashCode());
	result = prime * result + ((fkIaId == null) ? 0 : fkIaId.hashCode());
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
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
	IAttivitadyn2modellitId other = (IAttivitadyn2modellitId) obj;
	if (fkD2mtId == null) {
	    if (other.fkD2mtId != null) {
		return false;
	    }
	} else if (!fkD2mtId.equals(other.fkD2mtId)) {
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
	return true;
    }
}
