package it.gruppoinit.pal.gp.pay.domain;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;

public class PayMessaggiRabbitPagamentiId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5623478001078525945L;
    private String idcomune;
    private String fkMsgrabbitGuid;
    private String uuidPosizioneDeb;

    @Column(name = "IDCOMUNE")
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_MSGRABBIT_GUID")
    public String getFkMsgrabbitGuid() {

	return fkMsgrabbitGuid;
    }

    public void setFkMsgrabbitGuid(String fkMsgrabbitGuid) {

	this.fkMsgrabbitGuid = fkMsgrabbitGuid;
    }

    @Column(name = "UUID_POSIZIONE_DEB")
    public String getUuidPosizioneDeb() {

	return uuidPosizioneDeb;
    }

    public void setUuidPosizioneDeb(String uuidPosizioneDeb) {

	this.uuidPosizioneDeb = uuidPosizioneDeb;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((getIdcomune() == null) ? 0 : this.getIdcomune().hashCode());
	result = prime * result + ((StringUtils.isBlank(getFkMsgrabbitGuid())) ? 0 : this.getFkMsgrabbitGuid().hashCode());
	result = prime * result + ((StringUtils.isBlank(this.getUuidPosizioneDeb())) ? 0 : this.getUuidPosizioneDeb().hashCode());
	return result;
    }

    @Override
    public boolean equals(Object other) {

	if (this == other)
	    return true;
	if (other == null)
	    return false;
	if (!(other instanceof PayMessaggiRabbitPagamentiId))
	    return false;
	PayMessaggiRabbitPagamentiId castOther = (PayMessaggiRabbitPagamentiId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getUuidPosizioneDeb() == castOther.getUuidPosizioneDeb())
			|| (!StringUtils.isBlank(this.getUuidPosizioneDeb()) && !StringUtils.isBlank(castOther.getUuidPosizioneDeb())
				&& this.getUuidPosizioneDeb().equalsIgnoreCase(castOther.getUuidPosizioneDeb())))
		&& ((this.getFkMsgrabbitGuid() == castOther.getFkMsgrabbitGuid()) || (this.getFkMsgrabbitGuid() != null
			&& castOther.getFkMsgrabbitGuid() != null && this.getFkMsgrabbitGuid().equals(castOther.getFkMsgrabbitGuid())));
    }
}
