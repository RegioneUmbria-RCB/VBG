package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OConfigurazioneId implements Serializable {

    private static final long serialVersionUID = 1873979906091992152L;
    private String idcomune;
    private String software;

    public OConfigurazioneId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
	this.software = ORMHelper.getSoftware();
    }

    public OConfigurazioneId(String idcomune, String software) {

	this();
	this.idcomune = idcomune;
	this.software = software;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
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
	OConfigurazioneId other = (OConfigurazioneId) obj;
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
