package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class LayoutpagineId implements Serializable {

    private static final long serialVersionUID = 3440592291285775159L;
    private String idcomune;
    private String lpOggetto;
    private String lpPagina;
    private String software;

    public LayoutpagineId() {

	this.idcomune = ORMHelper.getIdcomune();
	this.software = ORMHelper.getSoftware();
    }

    public LayoutpagineId(String lpOggetto, String lpPagina) {

	this();
	this.lpOggetto = lpOggetto;
	this.lpPagina = lpPagina;
    }

    public LayoutpagineId(String lpOggetto, String lpPagina, String software) {

	this();
	this.lpOggetto = lpOggetto;
	this.lpPagina = lpPagina;
	this.software = software;
    }

    public LayoutpagineId(String idcomune, String lpOggetto, String lpPagina, String software) {

	this.idcomune = idcomune;
	this.lpOggetto = lpOggetto;
	this.lpPagina = lpPagina;
	this.software = software;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getLpOggetto() {

	return lpOggetto;
    }

    public void setLpOggetto(String lpOggetto) {

	this.lpOggetto = lpOggetto;
    }

    public String getLpPagina() {

	return lpPagina;
    }

    public void setLpPagina(String lpPagina) {

	this.lpPagina = lpPagina;
    }

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
	result = prime * result + ((lpOggetto == null) ? 0 : lpOggetto.hashCode());
	result = prime * result + ((lpPagina == null) ? 0 : lpPagina.hashCode());
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
	LayoutpagineId other = (LayoutpagineId) obj;
	if (idcomune == null) {
	    if (other.idcomune != null) {
		return false;
	    }
	} else if (!idcomune.equals(other.idcomune)) {
	    return false;
	}
	if (lpOggetto == null) {
	    if (other.lpOggetto != null) {
		return false;
	    }
	} else if (!lpOggetto.equals(other.lpOggetto)) {
	    return false;
	}
	if (lpPagina == null) {
	    if (other.lpPagina != null) {
		return false;
	    }
	} else if (!lpPagina.equals(other.lpPagina)) {
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
