package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class AppIoCodaStatiId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5394433855372594962L;
    private String idcomune;
    private String guid;
    private String stato;
    private Date data;

    public AppIoCodaStatiId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public AppIoCodaStatiId(String guid, String stato, Date data) {

	this();
	this.guid = guid;
	this.stato = stato;
	this.data = data;
    }

    public AppIoCodaStatiId(String idComune, String guid, String stato, Date data) {

	this();
	this.idcomune = idComune;
	this.guid = guid;
	this.stato = stato;
	this.data = data;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "GUID", nullable = false, length = 40)
    public String getGuid() {

	return guid;
    }

    public void setGuid(String guid) {

	this.guid = guid;
    }

    @Column(name = "STATO", nullable = false, length = 50)
    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA", nullable = false)
    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    @Override
    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof AppIoCodaStatiId))
	    return false;
	AppIoCodaStatiId castOther = (AppIoCodaStatiId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune()))) //
		&& ((this.getGuid() == castOther.getGuid()) || (this.getGuid() != null && // 
			castOther.getGuid() != null && this.getGuid().equals(castOther.getGuid()))) //
		&& ((this.getStato() == castOther.getStato()) || (this.getStato() != null && // 
			castOther.getStato() != null && this.getStato().equals(castOther.getStato())))//
		&& ((this.getData() == castOther.getData()) || (this.getData() != null && // 
			castOther.getData() != null && this.getData().equals(castOther.getData())));
    }

    @Override
    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getGuid() == null ? 0 : this.getGuid().hashCode());
	result = 37 * result + (getStato() == null ? 0 : this.getStato().hashCode());
	result = 37 * result + (getData() == null ? 0 : this.getData().hashCode());
	return result;
    }
}
