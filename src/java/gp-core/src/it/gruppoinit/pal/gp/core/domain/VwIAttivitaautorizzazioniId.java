package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VwIAttivitaautorizzazioniId implements Serializable {

    private static final long serialVersionUID = -7591315331066187181L;
    private String idcomune;
    private Integer id;

    public VwIAttivitaautorizzazioniId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public VwIAttivitaautorizzazioniId(Integer id) {

	super();
	this.id = id;
	this.idcomune = ORMHelper.getIdcomune();
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "ID", nullable = false, precision = 6, scale = 0)
    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
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
	VwIAttivitaautorizzazioniId other = (VwIAttivitaautorizzazioniId) obj;
	if (id == null) {
	    if (other.id != null) {
		return false;
	    }
	} else if (!id.equals(other.id)) {
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
