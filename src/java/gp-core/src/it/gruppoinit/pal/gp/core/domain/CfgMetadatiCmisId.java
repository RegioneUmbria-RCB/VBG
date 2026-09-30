package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CfgMetadatiCmisId implements Serializable {

    private static final long serialVersionUID = -1684118850381307177L;
    private String idcomune;
    private String fkMetadatoBase;

    public CfgMetadatiCmisId() {

    }

    public CfgMetadatiCmisId(String idcomune, String fkMetadatoBase) {

	this.idcomune = idcomune;
	this.fkMetadatoBase = fkMetadatoBase;
    }

    public CfgMetadatiCmisId(String fkMetadatoBase) {

	this.idcomune = ORMHelper.getIdcomune();
	this.fkMetadatoBase = fkMetadatoBase;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_METADATO_BASE", nullable = false, length = 30)
    public String getFkMetadatoBase() {

	return fkMetadatoBase;
    }

    public void setFkMetadatoBase(String fkMetadatoBase) {

	this.fkMetadatoBase = fkMetadatoBase;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((fkMetadatoBase == null) ? 0 : fkMetadatoBase.hashCode());
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
	CfgMetadatiCmisId other = (CfgMetadatiCmisId) obj;
	if (fkMetadatoBase == null) {
	    if (other.fkMetadatoBase != null) {
		return false;
	    }
	} else if (!fkMetadatoBase.equals(other.fkMetadatoBase)) {
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
