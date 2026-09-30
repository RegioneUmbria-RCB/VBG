package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;

public class MessaggiRabbitMovimentiId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5817996933506661544L;
    private String idcomune;
    private String fkMsgrabbitGuid;
    private String uuIdMovimento;

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

    @Column(name = "UUID_MOVIMENTO")
    public String getUuIdMovimento() {

	return uuIdMovimento;
    }

    public void setUuIdMovimento(String uuIdMovimento) {

	this.uuIdMovimento = uuIdMovimento;
    }

    public static long getSerialversionuid() {

	return serialVersionUID;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + (StringUtils.isBlank(this.getUuIdMovimento()) ? 0 : this.getUuIdMovimento().hashCode());
	result = prime * result + ((getFkMsgrabbitGuid() == null) ? 0 : this.getFkMsgrabbitGuid().hashCode());
	result = prime * result + ((getIdcomune() == null) ? 0 : this.getIdcomune().hashCode());
	return result;
    }

    @Override
    public boolean equals(Object other) {

	if (this == other)
	    return true;
	if (other == null)
	    return false;
	if (other instanceof MessaggiRabbitMovimentiId)
	    return false;
	MessaggiRabbitMovimentiId castOther = (MessaggiRabbitMovimentiId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getUuIdMovimento() == castOther.getUuIdMovimento())
			|| (!StringUtils.isBlank(this.getUuIdMovimento()) && !StringUtils.isBlank(castOther.getUuIdMovimento())
				&& this.getUuIdMovimento().equalsIgnoreCase(castOther.getUuIdMovimento())))
		&& ((this.getFkMsgrabbitGuid() == castOther.getFkMsgrabbitGuid()) || (this.getFkMsgrabbitGuid() != null
			&& castOther.getFkMsgrabbitGuid() != null && this.getFkMsgrabbitGuid().equals(castOther.getFkMsgrabbitGuid())));
    }
}
