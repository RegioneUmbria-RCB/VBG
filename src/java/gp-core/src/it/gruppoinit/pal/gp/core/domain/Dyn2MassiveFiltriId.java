package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class Dyn2MassiveFiltriId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2987706530624019562L;
    private String idcomune;
    private Integer fkidelaborazione;
    private String filtro;

    public Dyn2MassiveFiltriId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public Dyn2MassiveFiltriId(Integer fkidelaborazione, String filtro) {

	super();
	this.idcomune = ORMHelper.getIdcomune();
	this.fkidelaborazione = fkidelaborazione;
	this.filtro = filtro;
    }

    public Dyn2MassiveFiltriId(String idcomune, Integer fkidelaborazione, String filtro) {

	super();
	this.idcomune = idcomune;
	this.fkidelaborazione = fkidelaborazione;
	this.filtro = filtro;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FKIDELABORAZIONE", nullable = false, precision = 10, scale = 0)
    public Integer getFkidelaborazione() {

	return fkidelaborazione;
    }

    public void setFkidelaborazione(Integer fkidelaborazione) {

	this.fkidelaborazione = fkidelaborazione;
    }

    @Column(name = "FILTRO", nullable = false, length = 50)
    public String getFiltro() {

	return filtro;
    }

    public void setFiltro(String filtro) {

	this.filtro = filtro;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((filtro == null) ? 0 : filtro.hashCode());
	result = prime * result + ((fkidelaborazione == null) ? 0 : fkidelaborazione.hashCode());
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
	Dyn2MassiveFiltriId other = (Dyn2MassiveFiltriId) obj;
	if (filtro == null) {
	    if (other.filtro != null) {
		return false;
	    }
	} else if (!filtro.equals(other.filtro)) {
	    return false;
	}
	if (fkidelaborazione == null) {
	    if (other.fkidelaborazione != null) {
		return false;
	    }
	} else if (!fkidelaborazione.equals(other.fkidelaborazione)) {
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
