package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OConfigurazionetipionereId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9146242267449655653L;
    private String idcomune;
    private String fkBtoId;
    private String software;

    public OConfigurazionetipionereId() {

	this.idcomune = ORMHelper.getIdcomune();
	this.software = ORMHelper.getSoftware();
    }

    public OConfigurazionetipionereId(String fkBtoId) {

	this();
	this.fkBtoId = fkBtoId;
    }

    public OConfigurazionetipionereId(String idcomune, String fkBtoId, String software) {

	this();
	this.idcomune = idcomune;
	this.fkBtoId = fkBtoId;
	this.software = software;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_BTO_ID", nullable = false, length = 3)
    public String getFkBtoId() {

	return fkBtoId;
    }

    public void setFkBtoId(String fkBtoId) {

	this.fkBtoId = fkBtoId;
    }

    @Column(name = "SOFTWARE", nullable = false, length = 2)
    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((fkBtoId == null) ? 0 : fkBtoId.hashCode());
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	result = prime * result + ((software == null) ? 0 : software.hashCode());
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
	OConfigurazionetipionereId other = (OConfigurazionetipionereId) obj;
	if (fkBtoId == null) {
	    if (other.fkBtoId != null) {
		return false;
	    }
	} else if (!fkBtoId.equals(other.fkBtoId)) {
	    return false;
	}
	if (idcomune == null) {
	    if (other.idcomune != null) {
		return false;
	    }
	} else if (!idcomune.equals(other.idcomune)) {
	    return false;
	}
	if (software == null) {
	    if (other.software != null) {
		return false;
	    }
	} else if (!software.equals(other.software)) {
	    return false;
	}
	return true;
    }
}
