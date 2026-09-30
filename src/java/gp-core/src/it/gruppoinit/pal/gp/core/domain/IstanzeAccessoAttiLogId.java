package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.NotNull;

@Embeddable
public class IstanzeAccessoAttiLogId implements Serializable {

    private static final long serialVersionUID = -6573524912085113670L;
    private String idcomune;
    private String uuid;

    public IstanzeAccessoAttiLogId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public IstanzeAccessoAttiLogId(String uuid) {

	this();
	this.uuid = uuid;
    }

    public IstanzeAccessoAttiLogId(String idcomune, String uuid) {

	this();
	this.idcomune = idcomune;
	this.uuid = uuid;
    }

    @NotNull
    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @NotNull
    @Column(name = "UUID", nullable = false, length = 50)
    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof IstanzeAccessoAttiLogId))
	    return false;
	IstanzeAccessoAttiLogId castOther = (IstanzeAccessoAttiLogId) other;
	return ((this.getIdcomune() == castOther.getIdcomune()) || (this.getIdcomune() != null && castOther.getIdcomune() != null && this
		.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getUuid() == castOther.getUuid()) || (this.getUuid() != null && castOther.getUuid() != null && this.getUuid().equals(
			castOther.getUuid())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getUuid() == null ? 0 : this.getUuid().hashCode());
	return result;
    }
}
