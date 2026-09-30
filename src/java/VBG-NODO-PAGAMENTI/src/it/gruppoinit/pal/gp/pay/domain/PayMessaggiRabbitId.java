package it.gruppoinit.pal.gp.pay.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class PayMessaggiRabbitId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3232530436193028815L;
    private String idcomune;
    private String guid;

    public PayMessaggiRabbitId() {

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
	if (other instanceof PayMessaggiRabbitId)
	    return false;
	PayMessaggiRabbitId castOther = (PayMessaggiRabbitId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getGuid() == castOther.getGuid())
			|| (this.getGuid() != null && castOther.getGuid() != null && this.getGuid().equals(castOther.getGuid())));
    }
}
