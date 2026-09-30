package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class CommediliziePareriTmovId implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2628155850122446034L;
    private String idcomune;
    private String software;
    private Integer fkCommedpareriId;

    public CommediliziePareriTmovId() {

	this.idcomune = ORMHelper.getIdcomune();
	this.software = ORMHelper.getSoftware();
    }

    public CommediliziePareriTmovId(String software, Integer fkCommedpareriId) {

	this.software = software;
	this.fkCommedpareriId = fkCommedpareriId;
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

    @Column(name = "FK_COMMEDPARERI_ID", nullable = false, precision = 4, scale = 0)
    public Integer getFkCommedpareriId() {

	return fkCommedpareriId;
    }

    public void setFkCommedpareriId(Integer fkCommedpareriId) {

	this.fkCommedpareriId = fkCommedpareriId;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	result = prime * result + ((software == null) ? 0 : software.hashCode());
	result = prime * result + ((fkCommedpareriId == null) ? 0 : fkCommedpareriId.hashCode());
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
	CommediliziePareriTmovId other = (CommediliziePareriTmovId) obj;
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
	if (fkCommedpareriId == null) {
	    if (other.fkCommedpareriId != null) {
		return false;
	    }
	} else if (!fkCommedpareriId.equals(other.fkCommedpareriId)) {
	    return false;
	}
	return true;
    }
}
