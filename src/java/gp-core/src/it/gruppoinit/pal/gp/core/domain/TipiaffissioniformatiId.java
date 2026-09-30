package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TipiaffissioniformatiId implements Serializable {

    private static final long serialVersionUID = -579206321866749446L;
    private String idcomune;
    private Integer fkTipoaffissione;
    private Integer fkFormato;

    public TipiaffissioniformatiId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public TipiaffissioniformatiId(Integer fkTipoaffissione, Integer fkFormato) {

	this();
	this.fkTipoaffissione = fkTipoaffissione;
	this.fkFormato = fkFormato;
    }

    public TipiaffissioniformatiId(String idcomune, Integer fkTipoaffissione, Integer fkFormato) {

	this();
	this.idcomune = idcomune;
	this.fkTipoaffissione = fkTipoaffissione;
	this.fkFormato = fkFormato;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_TIPOAFFISSIONE", nullable = false, precision = 4, scale = 0)
    public Integer getFkTipoaffissione() {

	return fkTipoaffissione;
    }

    public void setFkTipoaffissione(Integer fkTipoaffissione) {

	this.fkTipoaffissione = fkTipoaffissione;
    }

    @Column(name = "FK_FORMATO", nullable = false, precision = 4, scale = 0)
    public Integer getFkFormato() {

	return fkFormato;
    }

    public void setFkFormato(Integer fkFormato) {

	this.fkFormato = fkFormato;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((fkFormato == null) ? 0 : fkFormato.hashCode());
	result = prime * result + ((fkTipoaffissione == null) ? 0 : fkTipoaffissione.hashCode());
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
	TipiaffissioniformatiId other = (TipiaffissioniformatiId) obj;
	if (fkFormato == null) {
	    if (other.fkFormato != null) {
		return false;
	    }
	} else if (!fkFormato.equals(other.fkFormato)) {
	    return false;
	}
	if (fkTipoaffissione == null) {
	    if (other.fkTipoaffissione != null) {
		return false;
	    }
	} else if (!fkTipoaffissione.equals(other.fkTipoaffissione)) {
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
