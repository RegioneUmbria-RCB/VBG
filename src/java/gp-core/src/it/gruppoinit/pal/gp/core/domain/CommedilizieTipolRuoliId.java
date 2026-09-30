package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class CommedilizieTipolRuoliId implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1013134535296728076L;
    private String idcomune;
    private Integer fkCommeditipoId;
    private Integer fkRuoliId;

    public CommedilizieTipolRuoliId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public CommedilizieTipolRuoliId(Integer fkCommeditipoId, Integer fkRuoliId) {

	this();
	this.fkCommeditipoId = fkCommeditipoId;
	this.fkRuoliId = fkRuoliId;
    }

    public CommedilizieTipolRuoliId(String idcomune, Integer fkCommeditipoId, Integer fkRuoliId) {

	this.idcomune = idcomune;
	this.fkCommeditipoId = fkCommeditipoId;
	this.fkRuoliId = fkRuoliId;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_COMMEDITIPO_ID", nullable = false, precision = 4, scale = 0)
    public Integer getFkCommeditipoId() {

	return fkCommeditipoId;
    }

    public void setFkCommeditipoId(Integer fkCommeditipoId) {

	this.fkCommeditipoId = fkCommeditipoId;
    }

    @Column(name = "FK_RUOLI_ID", nullable = false, precision = 4, scale = 0)
    public Integer getFkRuoliId() {

	return fkRuoliId;
    }

    public void setFkRuoliId(Integer fkRuoliId) {

	this.fkRuoliId = fkRuoliId;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((fkCommeditipoId == null) ? 0 : fkCommeditipoId.hashCode());
	result = prime * result + ((fkRuoliId == null) ? 0 : fkRuoliId.hashCode());
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
	CommedilizieTipolRuoliId other = (CommedilizieTipolRuoliId) obj;
	if (fkCommeditipoId == null) {
	    if (other.fkCommeditipoId != null) {
		return false;
	    }
	} else if (!fkCommeditipoId.equals(other.fkCommeditipoId)) {
	    return false;
	}
	if (fkRuoliId == null) {
	    if (other.fkRuoliId != null) {
		return false;
	    }
	} else if (!fkRuoliId.equals(other.fkRuoliId)) {
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
