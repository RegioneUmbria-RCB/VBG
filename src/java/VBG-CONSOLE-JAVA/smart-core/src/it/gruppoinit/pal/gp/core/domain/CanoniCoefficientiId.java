package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CanoniCoefficientiId implements Serializable {

    private static final long serialVersionUID = -5327936077011219333L;
    private String idcomune;
    private Integer anno;
    private Integer fkTsid;
    private Integer fkCcid;

    public CanoniCoefficientiId(Integer anno, Integer fkTsid, Integer fkCcid) {

	this();
	this.anno = anno;
	this.fkTsid = fkTsid;
	this.fkCcid = fkCcid;
    }

    public CanoniCoefficientiId(String idcomune, Integer anno, Integer fkTsid, Integer fkCcid) {

	this();
	this.idcomune = idcomune;
	this.anno = anno;
	this.fkTsid = fkTsid;
	this.fkCcid = fkCcid;
    }

    public CanoniCoefficientiId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    @Column(name = "IDCOMUNE", length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "ANNO", precision = 4, scale = 0)
    public Integer getAnno() {

	return anno;
    }

    public void setAnno(Integer anno) {

	this.anno = anno;
    }

    @Column(name = "FK_TSID", precision = 4, scale = 0)
    public Integer getFkTsid() {

	return fkTsid;
    }

    public void setFkTsid(Integer fkTsid) {

	this.fkTsid = fkTsid;
    }

    @Column(name = "FK_CCID", precision = 4, scale = 0)
    public Integer getFkCcid() {

	return fkCcid;
    }

    public void setFkCcid(Integer fkCcid) {

	this.fkCcid = fkCcid;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((anno == null) ? 0 : anno.hashCode());
	result = prime * result + ((fkCcid == null) ? 0 : fkCcid.hashCode());
	result = prime * result + ((fkTsid == null) ? 0 : fkTsid.hashCode());
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
	CanoniCoefficientiId other = (CanoniCoefficientiId) obj;
	if (anno == null) {
	    if (other.anno != null) {
		return false;
	    }
	} else if (!anno.equals(other.anno)) {
	    return false;
	}
	if (fkCcid == null) {
	    if (other.fkCcid != null) {
		return false;
	    }
	} else if (!fkCcid.equals(other.fkCcid)) {
	    return false;
	}
	if (fkTsid == null) {
	    if (other.fkTsid != null) {
		return false;
	    }
	} else if (!fkTsid.equals(other.fkTsid)) {
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
