package it.gruppoinit.pal.gp.pay.domain;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;

public class PayPosdebRifClientId implements Serializable {

    public PayPosdebRifClientId() {

	super();
    }

    public PayPosdebRifClientId(String idcomune, String guid) {

	this();
	this.idcomune = idcomune;
	this.guid = guid;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 4401178339188884599L;
    private String idcomune;
    private String guid;

    @Column(name = "IDCOMUNE")
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

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
	result = prime * result + ((getIdcomune() == null) ? 0 : this.getIdcomune().hashCode());
	result = prime * result + ((StringUtils.isBlank(getGuid())) ? 0 : this.getGuid().hashCode());
	return result;
    }

    @Override
    public boolean equals(Object other) {

	if (this == other)
	    return true;
	if (other == null)
	    return false;
	if (!(other instanceof PayPosdebRifClientId))
	    return false;
	PayPosdebRifClientId castOther = (PayPosdebRifClientId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getGuid() == castOther.getGuid()) || (!StringUtils.isBlank(this.getGuid()) && !StringUtils.isBlank(castOther.getGuid())
			&& this.getGuid().equalsIgnoreCase(castOther.getGuid())));
    }
}
