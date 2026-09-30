package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class Dyn2MassiveistanzeId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1472338770271070631L;
    private String idcomune;
    private Integer fkidelaborazione;
    private Integer codiceistanza;

    public Dyn2MassiveistanzeId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public Dyn2MassiveistanzeId(String idcomune, Integer fkidelaborazione, Integer codiceistanza) {

	super();
	this.idcomune = idcomune;
	this.fkidelaborazione = fkidelaborazione;
	this.codiceistanza = codiceistanza;
    }

    public Dyn2MassiveistanzeId(Integer fkidelaborazione, Integer codiceistanza) {

	super();
	this.idcomune = ORMHelper.getIdcomune();
	this.fkidelaborazione = fkidelaborazione;
	this.codiceistanza = codiceistanza;
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

    @Column(name = "FKIDELABORAZIONE", nullable = false, precision = 6, scale = 0)
    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((codiceistanza == null) ? 0 : codiceistanza.hashCode());
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
	Dyn2MassiveistanzeId other = (Dyn2MassiveistanzeId) obj;
	if (codiceistanza == null) {
	    if (other.codiceistanza != null) {
		return false;
	    }
	} else if (!codiceistanza.equals(other.codiceistanza)) {
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
