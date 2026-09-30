package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;

public class MessaggiRabbitIstanzeId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6846260346450888151L;
    private String idcomune;
    private String fkMsgrabbitGuid;
    private String uuIdIstanza;

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

    @Column(name = "UUID_ISTANZA")
    public String getUuIdIstanza() {

	return uuIdIstanza;
    }

    public void setUuIdIstanza(String uuIdIstanza) {

	this.uuIdIstanza = uuIdIstanza;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((StringUtils.isBlank(this.getUuIdIstanza())) ? 0 : this.getUuIdIstanza().hashCode());
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
	if (!(other instanceof MessaggiRabbitIstanzeId))
	    return false;
	MessaggiRabbitIstanzeId castOther = (MessaggiRabbitIstanzeId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getUuIdIstanza() == castOther.getUuIdIstanza()) || (!StringUtils.isBlank(this.getUuIdIstanza())
			&& !StringUtils.isBlank(castOther.getUuIdIstanza()) && this.getUuIdIstanza().equalsIgnoreCase(castOther.getUuIdIstanza())))
		&& ((this.getFkMsgrabbitGuid() == castOther.getFkMsgrabbitGuid()) || (this.getFkMsgrabbitGuid() != null
			&& castOther.getFkMsgrabbitGuid() != null && this.getFkMsgrabbitGuid().equals(castOther.getFkMsgrabbitGuid())));
    }
}
