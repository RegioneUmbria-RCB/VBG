package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CollaudoverificheId implements Serializable {

    private static final long serialVersionUID = 5513010575763245126L;
    private String idcomune;
    private Integer codiceistanza;
    private Integer codiceamministrazione;

    public CollaudoverificheId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public CollaudoverificheId(Integer codiceistanza, Integer codiceamministrazione) {

	this();
	this.codiceistanza = codiceistanza;
	this.codiceamministrazione = codiceamministrazione;
    }

    public CollaudoverificheId(String idcomune, Integer codiceistanza, Integer codiceamministrazione) {

	this();
	this.idcomune = idcomune;
	this.codiceistanza = codiceistanza;
	this.codiceamministrazione = codiceamministrazione;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "CODICEISTANZA", nullable = false, precision = 6, scale = 0)
    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    @Column(name = "CODICEAMMINISTRAZIONE", nullable = false, precision = 4, scale = 0)
    public Integer getCodiceamministrazione() {

	return codiceamministrazione;
    }

    public void setCodiceamministrazione(Integer codiceamministrazione) {

	this.codiceamministrazione = codiceamministrazione;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((codiceamministrazione == null) ? 0 : codiceamministrazione.hashCode());
	result = prime * result + ((codiceistanza == null) ? 0 : codiceistanza.hashCode());
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
	CollaudoverificheId other = (CollaudoverificheId) obj;
	if (codiceamministrazione == null) {
	    if (other.codiceamministrazione != null) {
		return false;
	    }
	} else if (!codiceamministrazione.equals(other.codiceamministrazione)) {
	    return false;
	}
	if (codiceistanza == null) {
	    if (other.codiceistanza != null) {
		return false;
	    }
	} else if (!codiceistanza.equals(other.codiceistanza)) {
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
