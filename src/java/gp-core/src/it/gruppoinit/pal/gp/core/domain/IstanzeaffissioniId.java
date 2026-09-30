package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IstanzeaffissioniId implements Serializable {

    private static final long serialVersionUID = -5133700063328990586L;
    private String idcomune;
    private Integer codiceistanza;
    private Integer id;

    public IstanzeaffissioniId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public IstanzeaffissioniId(Integer codiceistanza, Integer id) {

	this();
	this.codiceistanza = codiceistanza;
	this.id = id;
    }

    public IstanzeaffissioniId(String idcomune, Integer codiceistanza, Integer id) {

	this();
	this.idcomune = idcomune;
	this.codiceistanza = codiceistanza;
	this.id = id;
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

    @Column(name = "ID", nullable = false, precision = 3, scale = 0)
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
	result = prime * result + ((codiceistanza == null) ? 0 : codiceistanza.hashCode());
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
	IstanzeaffissioniId other = (IstanzeaffissioniId) obj;
	if (codiceistanza == null) {
	    if (other.codiceistanza != null) {
		return false;
	    }
	} else if (!codiceistanza.equals(other.codiceistanza)) {
	    return false;
	}
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
