package it.gruppoinit.pal.gp.core.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class RicalcoloAreeIstanzeId implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6889571953559416681L;
    private String idcomune;
    private String ricalcoloId;
    private String istanzeUuid;

    public RicalcoloAreeIstanzeId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public RicalcoloAreeIstanzeId(String istanzeUuid) {
	this.idcomune = ORMHelper.getIdcomune();
	this.ricalcoloId = UUID.randomUUID().toString();
	this.istanzeUuid = istanzeUuid;
    }

    public RicalcoloAreeIstanzeId(String ricalcoloId, String istanzeUuid) {

	this.idcomune = ORMHelper.getIdcomune();
	this.ricalcoloId = ricalcoloId;
	this.istanzeUuid = istanzeUuid;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "RICALCOLO_ID")
    public String getRicalcoloId() {

	return ricalcoloId;
    }

    public void setRicalcoloId(String ricalcoloId) {

	this.ricalcoloId = ricalcoloId;
    }

    @Column(name = "ISTANZE_UUID")
    public String getIstanzeUuid() {

	return istanzeUuid;
    }

    public void setIstanzeUuid(String istanzeUuid) {

	this.istanzeUuid = istanzeUuid;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	result = prime * result + ((istanzeUuid == null) ? 0 : istanzeUuid.hashCode());
	result = prime * result + ((ricalcoloId == null) ? 0 : ricalcoloId.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	RicalcoloAreeIstanzeId other = (RicalcoloAreeIstanzeId) obj;
	if (idcomune == null) {
	    if (other.idcomune != null)
		return false;
	} else if (!idcomune.equals(other.idcomune))
	    return false;
	if (istanzeUuid == null) {
	    if (other.istanzeUuid != null)
		return false;
	} else if (!istanzeUuid.equals(other.istanzeUuid))
	    return false;
	if (ricalcoloId == null) {
	    if (other.ricalcoloId != null)
		return false;
	} else if (!ricalcoloId.equals(other.ricalcoloId))
	    return false;
	return true;
    }
}
