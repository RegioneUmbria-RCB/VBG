package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Dyn2ModelliScriptId implements Serializable {

    private static final long serialVersionUID = -4522747005552582840L;
    private String idcomune;
    private Integer fkD2mtId;
    private String evento;

    public Dyn2ModelliScriptId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public Dyn2ModelliScriptId(Integer fkD2mtId, String evento) {

	this();
	this.fkD2mtId = fkD2mtId;
	this.evento = evento;
    }

    public Dyn2ModelliScriptId(String idcomune, Integer fkD2mtId, String evento) {

	this();
	this.idcomune = idcomune;
	this.fkD2mtId = fkD2mtId;
	this.evento = evento;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_D2MT_ID", nullable = false, precision = 6, scale = 0)
    public Integer getFkD2mtId() {

	return fkD2mtId;
    }

    public void setFkD2mtId(Integer fkD2mtId) {

	this.fkD2mtId = fkD2mtId;
    }

    @Column(name = "EVENTO", nullable = false, length = 15)
    public String getEvento() {

	return evento;
    }

    public void setEvento(String evento) {

	this.evento = evento;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((evento == null) ? 0 : evento.hashCode());
	result = prime * result + ((fkD2mtId == null) ? 0 : fkD2mtId.hashCode());
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
	Dyn2ModelliScriptId other = (Dyn2ModelliScriptId) obj;
	if (evento == null) {
	    if (other.evento != null) {
		return false;
	    }
	} else if (!evento.equals(other.evento)) {
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
	return true;
    }
}
