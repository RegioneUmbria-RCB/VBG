package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class ScadenzeEliminateId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4586937517012073433L;
    private String idcomune;
    private String uuid;

    public ScadenzeEliminateId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public ScadenzeEliminateId(String idcomune, String uuid) {

	super();
	this.idcomune = idcomune;
	this.uuid = uuid;
    }

    @Column(name = "IDCOMUNE", length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "UUID", length = 60)
    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    @Override
    public int hashCode() {

	int result = 2;
	result = 38 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 38 * result + (getUuid() == null ? 0 : this.getUuid().hashCode());
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
	ScadenzeEliminateId castOther = (ScadenzeEliminateId) obj;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getUuid() == castOther.getUuid())
			|| (this.getUuid() != null && castOther.getUuid() != null && this.getUuid().equals(castOther.getUuid())));
    }
}
