package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Dyn2CampiScriptId implements Serializable {

    private static final long serialVersionUID = 6796300485536332509L;
    private String idcomune;
    private Integer fkD2cId;
    private String evento;

    public Dyn2CampiScriptId(String idcomune, Integer fkD2cId, String evento) {

	this();
	this.idcomune = idcomune;
	this.fkD2cId = fkD2cId;
	this.evento = evento;
    }

    public Dyn2CampiScriptId(Integer fkD2cId, String evento) {

	this();
	this.fkD2cId = fkD2cId;
	this.evento = evento;
    }

    public Dyn2CampiScriptId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_D2C_ID", nullable = false, precision = 6, scale = 0)
    public Integer getFkD2cId() {

	return fkD2cId;
    }

    public void setFkD2cId(Integer fkD2cId) {

	this.fkD2cId = fkD2cId;
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
	result = prime * result + ((fkD2cId == null) ? 0 : fkD2cId.hashCode());
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
	Dyn2CampiScriptId other = (Dyn2CampiScriptId) obj;
	if (evento == null) {
	    if (other.evento != null) {
		return false;
	    }
	} else if (!evento.equals(other.evento)) {
	    return false;
	}
	if (fkD2cId == null) {
	    if (other.fkD2cId != null) {
		return false;
	    }
	} else if (!fkD2cId.equals(other.fkD2cId)) {
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
