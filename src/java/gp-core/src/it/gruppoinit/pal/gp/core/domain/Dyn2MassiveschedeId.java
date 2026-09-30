package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class Dyn2MassiveschedeId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2865461665303777861L;
    private String idcomune;
    private Integer fkidelaborazione;
    private Integer fkD2mtId;

    public Dyn2MassiveschedeId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public Dyn2MassiveschedeId(String idcomune, Integer fkidelaborazione, Integer fkD2mtId) {

	super();
	this.idcomune = idcomune;
	this.fkidelaborazione = fkidelaborazione;
	this.fkD2mtId = fkD2mtId;
    }

    public Dyn2MassiveschedeId(Integer fkidelaborazione, Integer fkD2mtId) {

	super();
	this.idcomune = ORMHelper.getIdcomune();
	this.fkidelaborazione = fkidelaborazione;
	this.fkD2mtId = fkD2mtId;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FKIDELABORAZIONE", nullable = false, precision = 10, scale = 0)
    public Integer getFkidelaborazione() {

	return fkidelaborazione;
    }

    public void setFkidelaborazione(Integer fkidelaborazione) {

	this.fkidelaborazione = fkidelaborazione;
    }

    @Column(name = "FK_D2MT_ID", nullable = false, precision = 10, scale = 0)
    public Integer getFkD2mtId() {

	return fkD2mtId;
    }

    public void setFkD2mtId(Integer fkD2mtId) {

	this.fkD2mtId = fkD2mtId;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((fkD2mtId == null) ? 0 : fkD2mtId.hashCode());
	result = prime * result + ((fkidelaborazione == null) ? 0 : fkidelaborazione.hashCode());
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
	Dyn2MassiveschedeId other = (Dyn2MassiveschedeId) obj;
	if (fkD2mtId == null) {
	    if (other.fkD2mtId != null) {
		return false;
	    }
	} else if (!fkD2mtId.equals(other.fkD2mtId)) {
	    return false;
	}
	if (fkidelaborazione == null) {
	    if (other.fkidelaborazione != null) {
		return false;
	    }
	} else if (!fkidelaborazione.equals(other.fkidelaborazione)) {
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
