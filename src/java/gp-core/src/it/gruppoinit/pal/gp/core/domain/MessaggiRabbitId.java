package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class MessaggiRabbitId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6519193771725443464L;
    private String idcomune;
    private String guid;

    public MessaggiRabbitId() {

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
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((getGuid() == null) ? 0 : this.getGuid().hashCode());
	result = prime * result + ((getIdcomune() == null) ? 0 : this.getIdcomune().hashCode());
	return result;
    }

    @Override
    public boolean equals(Object other) {

	if (this == other)
	    return true;
	if (other == null)
	    return false;
	if (other instanceof MessaggiRabbitId)
	    return false;
	MessaggiRabbitId castOther = (MessaggiRabbitId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getGuid() == castOther.getGuid())
			|| (this.getGuid() != null && castOther.getGuid() != null && this.getGuid().equals(castOther.getGuid())));
    }
}
