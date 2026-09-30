package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class CodaMessaggiRabbitId implements Serializable {

    public CodaMessaggiRabbitId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public CodaMessaggiRabbitId(String idcomune, String uuid) {

	super();
	this.idcomune = idcomune;
	this.uuid = uuid;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 7975181410649873220L;
    private String idcomune;
    private String uuid;

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "UUID")
    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((getIdcomune() == null) ? 0 : this.getIdcomune().hashCode());
	result = prime * result + ((getUuid() == null) ? 0 : this.getUuid().hashCode());
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
	CodaMessaggiRabbitId castOther = (CodaMessaggiRabbitId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getUuid() == castOther.getUuid())
			|| (this.getUuid() != null && castOther.getUuid() != null && this.getUuid().equals(castOther.getUuid())));
    }
}
