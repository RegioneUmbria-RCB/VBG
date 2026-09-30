package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class AppIoCodaId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5388423040606822663L;
    private String idcomune;
    private String guid;

    public AppIoCodaId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    @NotNull
    @Length(max = 6)
    @Column(name = "IDCOMUNE")
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @NotNull
    @Length(max = 40)
    @Column(name = "GUID")
    public String getGuid() {

	return guid;
    }

    public void setGuid(String guid) {

	this.guid = guid;
    }

    @Override
    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof AppIoCodaId))
	    return false;
	AppIoCodaId castOther = (AppIoCodaId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getGuid() == castOther.getGuid())
			|| (this.getGuid() != null && castOther.getGuid() != null && this.getGuid().equals(castOther.getGuid())));
    }

    @Override
    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getGuid() == null ? 0 : this.getGuid().hashCode());
	return result;
    }
}
