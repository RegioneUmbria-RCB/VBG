package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class AlberoprocComuniEsclusiId implements java.io.Serializable {

    private static final long serialVersionUID = 7076143097770443977L;
    private String idcomune;
    private Integer fkScid;
    private String codiceComune;

    public AlberoprocComuniEsclusiId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public AlberoprocComuniEsclusiId(Integer fkScid, String codiceComune) {

	this.idcomune = ORMHelper.getIdcomune();
	this.fkScid = fkScid;
	this.codiceComune = codiceComune;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return this.idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_SCID", nullable = false, precision = 10, scale = 0)
    public Integer getFkScid() {

	return fkScid;
    }

    public void setFkScid(Integer fkScid) {

	this.fkScid = fkScid;
    }

    @Column(name = "CODICECOMUNE", nullable = false, length = 5)
    public String getCodiceComune() {

	return codiceComune;
    }

    public void setCodiceComune(String codiceComune) {

	this.codiceComune = codiceComune;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((codiceComune == null) ? 0 : codiceComune.hashCode());
	result = prime * result + ((fkScid == null) ? 0 : fkScid.hashCode());
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
	AlberoprocComuniEsclusiId other = (AlberoprocComuniEsclusiId) obj;
	if (codiceComune == null) {
	    if (other.codiceComune != null) {
		return false;
	    }
	} else if (!codiceComune.equals(other.codiceComune)) {
	    return false;
	}
	if (fkScid == null) {
	    if (other.fkScid != null) {
		return false;
	    }
	} else if (!fkScid.equals(other.fkScid)) {
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