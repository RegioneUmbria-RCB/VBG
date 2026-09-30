package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TmpIstanzeId implements java.io.Serializable {

    private static final long serialVersionUID = 360482331255214951L;
    private String idcomune;
    private String sessionid;
    private String uuid;

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "SESSIONID", nullable = false, length = 60)
    public String getSessionid() {

	return sessionid;
    }

    public void setSessionid(String sessionid) {

	this.sessionid = sessionid;
    }

    @Column(name = "UUID", nullable = false, length = 60)
    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    @Override
    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof TmpIstanzeId))
	    return false;
	TmpIstanzeId castOther = (TmpIstanzeId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getSessionid() == castOther.getSessionid())
			|| (this.getSessionid() != null && castOther.getSessionid() != null && this.getSessionid().equals(castOther.getSessionid())))
		&& ((this.getUuid() == castOther.getUuid())
			|| (this.getUuid() != null && castOther.getUuid() != null && this.getUuid().equals(castOther.getUuid())));
    }

    @Override
    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getSessionid() == null ? 0 : this.getSessionid().hashCode());
	result = 37 * result + (getUuid() == null ? 0 : this.getUuid().hashCode());
	return result;
    }

    @Override
    public String toString() {

	ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
	toStringBuilder.append("idcomune", this.idcomune);
	toStringBuilder.append("sessionid", this.sessionid);
	toStringBuilder.append("uuid", this.uuid);
	return toStringBuilder.toString();
    }
}
