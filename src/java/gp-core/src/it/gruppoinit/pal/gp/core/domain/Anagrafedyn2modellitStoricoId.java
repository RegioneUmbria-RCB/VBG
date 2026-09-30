package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Anagrafedyn2modellitStoricoId implements Serializable {

    private static final long serialVersionUID = -5883914125631564970L;
    private String idcomune;
    private Integer idversione;
    private Integer codiceanagrafe;
    private Integer fkD2mtId;

    public Anagrafedyn2modellitStoricoId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public Anagrafedyn2modellitStoricoId(Integer idversione, Integer codiceanagrafe, Integer fkD2mtId) {

	this();
	this.idversione = idversione;
	this.codiceanagrafe = codiceanagrafe;
	this.fkD2mtId = fkD2mtId;
    }

    public Anagrafedyn2modellitStoricoId(String idcomune, Integer idversione, Integer codiceanagrafe, Integer fkD2mtId) {

	this();
	this.idcomune = idcomune;
	this.idversione = idversione;
	this.codiceanagrafe = codiceanagrafe;
	this.fkD2mtId = fkD2mtId;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "IDVERSIONE", nullable = false, precision = 3, scale = 0)
    public Integer getIdversione() {

	return idversione;
    }

    public void setIdversione(Integer idversione) {

	this.idversione = idversione;
    }

    @Column(name = "CODICEANAGRAFE", nullable = false, precision = 6, scale = 0)
    public Integer getCodiceanagrafe() {

	return codiceanagrafe;
    }

    public void setCodiceanagrafe(Integer codiceanagrafe) {

	this.codiceanagrafe = codiceanagrafe;
    }

    @Column(name = "FK_D2MT_ID", nullable = false, precision = 6, scale = 0)
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
	result = prime * result + ((codiceanagrafe == null) ? 0 : codiceanagrafe.hashCode());
	result = prime * result + ((fkD2mtId == null) ? 0 : fkD2mtId.hashCode());
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	result = prime * result + ((idversione == null) ? 0 : idversione.hashCode());
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
	Anagrafedyn2modellitStoricoId other = (Anagrafedyn2modellitStoricoId) obj;
	if (codiceanagrafe == null) {
	    if (other.codiceanagrafe != null) {
		return false;
	    }
	} else if (!codiceanagrafe.equals(other.codiceanagrafe)) {
	    return false;
	}
	if (fkD2mtId == null) {
	    if (other.fkD2mtId != null) {
		return false;
	    }
	} else if (!fkD2mtId.equals(other.fkD2mtId)) {
	    return false;
	}
	if (idcomune == null) {
	    if (other.idcomune != null) {
		return false;
	    }
	} else if (!idcomune.equals(other.idcomune)) {
	    return false;
	}
	if (idversione == null) {
	    if (other.idversione != null) {
		return false;
	    }
	} else if (!idversione.equals(other.idversione)) {
	    return false;
	}
	return true;
    }
}
