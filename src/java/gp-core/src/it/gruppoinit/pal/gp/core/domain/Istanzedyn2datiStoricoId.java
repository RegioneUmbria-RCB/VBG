package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Istanzedyn2datiStoricoId implements Serializable {

    private static final long serialVersionUID = 4954506591556382785L;
    private String idcomune;
    private Integer idversione;
    private Integer codiceistanza;
    private Integer fkD2mtId;
    private Integer fkD2cId;
    private Integer indice;
    private Integer indiceMolteplicita;

    public Istanzedyn2datiStoricoId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public Istanzedyn2datiStoricoId(Integer idversione, Integer codiceistanza, Integer fkD2mtId, Integer fkD2cId, Integer indice,
	    Integer indiceMolteplicita) {

	this();
	this.idversione = idversione;
	this.codiceistanza = codiceistanza;
	this.fkD2mtId = fkD2mtId;
	this.fkD2cId = fkD2cId;
	this.indice = indice;
	this.indiceMolteplicita = indiceMolteplicita;
    }

    public Istanzedyn2datiStoricoId(String idcomune, Integer idversione, Integer codiceistanza, Integer fkD2mtId, Integer fkD2cId, Integer indice,
	    Integer indiceMolteplicita) {

	this();
	this.idcomune = idcomune;
	this.idversione = idversione;
	this.codiceistanza = codiceistanza;
	this.fkD2mtId = fkD2mtId;
	this.fkD2cId = fkD2cId;
	this.indice = indice;
	this.indiceMolteplicita = indiceMolteplicita;
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

    @Column(name = "CODICEISTANZA", nullable = false, precision = 6, scale = 0)
    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    @Column(name = "FK_D2MT_ID", nullable = false, precision = 6, scale = 0)
    public Integer getFkD2mtId() {

	return fkD2mtId;
    }

    public void setFkD2mtId(Integer fkD2mtId) {

	this.fkD2mtId = fkD2mtId;
    }

    @Column(name = "FK_D2C_ID", nullable = false, precision = 6, scale = 0)
    public Integer getFkD2cId() {

	return fkD2cId;
    }

    public void setFkD2cId(Integer fkD2cId) {

	this.fkD2cId = fkD2cId;
    }

    @Column(name = "INDICE", nullable = false, precision = 2, scale = 0)
    public Integer getIndice() {

	return indice;
    }

    public void setIndice(Integer indice) {

	this.indice = indice;
    }

    @Column(name = "INDICE_MOLTEPLICITA", nullable = false, precision = 2, scale = 0)
    public Integer getIndiceMolteplicita() {

	return indiceMolteplicita;
    }

    public void setIndiceMolteplicita(Integer indiceMolteplicita) {

	this.indiceMolteplicita = indiceMolteplicita;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((codiceistanza == null) ? 0 : codiceistanza.hashCode());
	result = prime * result + ((fkD2cId == null) ? 0 : fkD2cId.hashCode());
	result = prime * result + ((fkD2mtId == null) ? 0 : fkD2mtId.hashCode());
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	result = prime * result + ((idversione == null) ? 0 : idversione.hashCode());
	result = prime * result + ((indice == null) ? 0 : indice.hashCode());
	result = prime * result + ((indiceMolteplicita == null) ? 0 : indiceMolteplicita.hashCode());
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
	Istanzedyn2datiStoricoId other = (Istanzedyn2datiStoricoId) obj;
	if (codiceistanza == null) {
	    if (other.codiceistanza != null) {
		return false;
	    }
	} else if (!codiceistanza.equals(other.codiceistanza)) {
	    return false;
	}
	if (fkD2cId == null) {
	    if (other.fkD2cId != null) {
		return false;
	    }
	} else if (!fkD2cId.equals(other.fkD2cId)) {
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
	if (indice == null) {
	    if (other.indice != null) {
		return false;
	    }
	} else if (!indice.equals(other.indice)) {
	    return false;
	}
	if (indiceMolteplicita == null) {
	    if (other.indiceMolteplicita != null) {
		return false;
	    }
	} else if (!indiceMolteplicita.equals(other.indiceMolteplicita)) {
	    return false;
	}
	return true;
    }
}
